# Meet Football

해외 축구 정보를 수집·제공하는 REST API 서버입니다.
외부 Football API에서 팀·선수 데이터를 마이그레이션하여 자체 DB에 저장하고, 클라이언트에 일관된 인터페이스로 제공합니다.

---

## 목차

- [프로젝트 목적](#프로젝트-목적)
- [사용 기술](#사용-기술)
- [프로젝트 아키텍처](#프로젝트-아키텍처)
- [패키지 구조](#패키지-구조)
- [주요 설계 결정](#주요-설계-결정)
- [API 명세](#api-명세)
- [로컬 실행](#로컬-실행)

---

## 프로젝트 목적

| 항목 | 내용 |
|---|---|
| 핵심 기능 | 외부 Football Data API(football-data.org)로부터 팀 데이터를 수집하고 자체 DB에 저장 |
| 제공 기능 | 팀, 유저 정보 조회 REST API |
| 기술 목표 | 헥사고날 아키텍처 기반 클린 코드, 도메인과 인프라 관심사 분리 |

---

## 사용 기술

| 분류 | 기술 | 버전 |
|---|---|---|
| Language | Kotlin | 1.9.24 |
| Framework | Spring Boot | 3.3.0 |
| Build | Gradle | 8.8 |
| ORM | Spring Data JPA / Hibernate | 3.3.0 |
| DB | MySQL | 8.x |
| Security | Spring Security | 3.3.0 |
| API Docs | SpringDoc OpenAPI (Swagger UI) | 2.5.0 |
| HTTP Client | Spring RestClient | (Spring Boot 내장) |
| Test | JUnit5, Spring Boot Test | 3.3.0 |
| JVM | Java | 17 |

---

## 프로젝트 아키텍처

### 헥사고날 아키텍처 (Ports & Adapters)

외부 시스템(Web, DB, 외부 API)과 비즈니스 로직을 명확하게 분리합니다.
의존성 방향은 항상 **외부 → 내부(도메인)** 단방향을 유지합니다.

```
┌─────────────────────────────────────────────────────────┐
│                   Inbound Adapters                      │
│         (REST Controller / @Web)                        │
└────────────────────────┬────────────────────────────────┘
                         │  Input Port (UseCase Interface)
                         ▼
┌─────────────────────────────────────────────────────────┐
│                  Application Core                       │
│                                                         │
│   ┌─────────────────────────────────────────────────┐   │
│   │              Domain Service (@UseCase)          │   │
│   │   - 비즈니스 규칙 구현                           │   │
│   │   - 외부 의존성 없음                             │   │
│   └─────────────────────────────────────────────────┘   │
│                                                         │
│   ┌──────────────┐        ┌────────────────────────┐    │
│   │ Domain Entity│        │   Output Port          │    │
│   │ (순수 Kotlin) │        │   (DB / HTTP Interface)│    │
│   └──────────────┘        └────────────────────────┘    │
└─────────────────────────────────────────────────────────┘
                         │  Output Port 구현체
                         ▼
┌─────────────────────────────────────────────────────────┐
│                  Outbound Adapters                      │
│     JPA Persistence (@Persistence) / HTTP Client       │
└─────────────────────────────────────────────────────────┘
```

### 레이어별 역할

| 레이어 | 패키지 | 어노테이션 | 역할 |
|---|---|---|---|
| Inbound Adapter | `adapter.{domain}.in.web` | `@Web` | HTTP 요청 수신, DTO 변환 |
| Input Port | `application.{domain}.port.in` | (interface) | UseCase 계약 정의 |
| Domain Service | `application.{domain}.domain.service` | `@UseCase` | 비즈니스 로직 |
| Domain Entity | `application.{domain}.domain.entity` | - | 순수 도메인 객체 |
| Output Port | `application.{domain}.port.out` | (interface) | DB/외부 API 계약 정의 |
| Outbound Adapter | `adapter.{domain}.out.persistence` | `@Persistence` | JPA 엔티티, Repository |
| HTTP Adapter | `adapter.base.out.httpclient` | `@HttpClient` | 외부 API 호출 |

### 도메인 흐름 예시 — 팀 마이그레이션

```
POST /api/teams/migration
        │
        ▼
MigrateTeamController   (@Web)
        │  MigrateTeamUseCase.migrate()
        ▼
MigrateTeamService      (@UseCase)
        │  HttpClientPort.get()          → football-data.org API 호출
        │  FindTeamByApiIdPort.find()    → 기존 팀 존재 여부 확인
        │  SaveTeamPort.save()           → 신규 저장
        │  UpdateTeamPort.update()       → 기존 데이터 갱신
        ▼
  JPA Adapters          (@Persistence)
        │
        ▼
     MySQL DB
```

### 에러 처리 — Result 타입

예외를 던지는 대신 `Result<T>` sealed class로 성공/실패를 명시적으로 표현합니다.

```kotlin
sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Fail<T>(val error: Error) : Result<T>()
}
```

컨트롤러 레이어의 `CustomResponseEntity`가 `Result`를 HTTP 응답으로 자동 변환합니다.

---

## 패키지 구조

```
src/main/kotlin/com/meetfootball/
│
├── MeetfootballApplication.kt
│
├── adapter/
│   ├── base/                              # 공통 어노테이션 & 응답 처리
│   │   ├── Web.kt                         # @RestController + @RequestMapping 합성
│   │   ├── UseCase.kt                     # @Component 합성 (도메인 서비스용)
│   │   ├── Persistence.kt                 # @Component 합성 (JPA 어댑터용)
│   │   ├── HttpClient.kt                  # @Component 합성 (HTTP 어댑터용)
│   │   └── in/web/response/
│   │       ├── ApiResponse.kt             # 응답 추상 기반 클래스
│   │       ├── CustomResponseEntity.kt    # Result<T> → ResponseEntity 변환
│   │       └── ExceptionResponse.kt      # 에러 응답 DTO
│   │
│   ├── team/
│   │   ├── in/web/
│   │   │   └── MigrateTeamController.kt  # POST /api/teams/migration
│   │   └── out/persistence/jpa/
│   │       ├── Team.kt                    # JPA 엔티티
│   │       ├── TeamRepository.kt          # Spring Data JPA
│   │       ├── TeamMapper.kt              # JPA ↔ Domain Entity 변환
│   │       ├── SaveTeamAdapter.kt
│   │       ├── UpdateTeamAdapter.kt
│   │       └── FindTeamByApiIdAdapter.kt
│   │
│   ├── user/
│   │   ├── in/web/
│   │   │   ├── FindUserByIdController.kt  # GET /api/users/by-id/{id}
│   │   │   └── response/
│   │   │       └── FindUserByIdResponse.kt
│   │   └── out/persistence/jpa/
│   │       ├── User.kt
│   │       ├── UserRepository.kt
│   │       ├── UserMapper.kt
│   │       └── FindUserByIdAdapter.kt
│   │
│   └── base/out/httpclient/restclient/
│       └── HttpClientAdapter.kt           # Spring RestClient 기반 외부 API 호출
│
├── application/
│   ├── base/
│   │   ├── Result.kt                      # 함수형 에러 핸들링
│   │   └── Error.kt                       # 도메인 에러 sealed class
│   │
│   ├── team/
│   │   ├── domain/
│   │   │   ├── entity/TeamEntity.kt
│   │   │   └── service/MigrateTeamService.kt
│   │   └── port/
│   │       ├── in/MigrateTeamUseCase.kt
│   │       └── out/                        # SaveTeamPort, UpdateTeamPort 등
│   │
│   └── user/
│       ├── domain/
│       │   ├── entity/UserEntity.kt
│       │   └── service/FindUserByIdService.kt
│       └── port/
│           ├── in/FindUserByIdUseCase.kt
│           └── out/FindUserByIdDbPort.kt
│
├── config/
│   ├── JpaConfig.kt                       # @EnableJpaAuditing
│   ├── SecurityConfig.kt                  # Spring Security 설정
│   └── SwaggerConfig.kt                   # OpenAPI 3.0 설정
│
├── enum/
│   └── ErrorCode.kt
│
└── sql/
    └── create-team.sql                    # 팀 테이블 DDL
```

---

## 주요 설계 결정

### 합성 어노테이션 (Meta-Annotation)

`@Web`, `@UseCase`, `@Persistence`, `@HttpClient`는 Spring 어노테이션을 합성한 커스텀 어노테이션입니다.
레이어 역할을 코드에서 명시적으로 표현하고, 전체 검색/필터링이 용이합니다.

### ddl-auto: validate

스키마 변경은 `sql/` 하위 DDL 파일로 직접 관리합니다.
Hibernate가 자동으로 스키마를 수정하지 않아 운영 환경 안전성을 확보합니다.

### Swagger UI

`/v1/api-docs/swagger` 경로에서 API 명세를 확인할 수 있습니다.

---

## API 명세

| Method | URL | 설명 |
|---|---|---|
| `GET` | `/api/users/by-id/{id}` | ID로 유저 조회 |
| `POST` | `/api/teams/migration` | 외부 API에서 팀 데이터 마이그레이션 |

전체 API 명세는 Swagger UI에서 확인하세요: `http://localhost:8080/v1/api-docs/swagger`

---

## 로컬 실행

**사전 요구사항:** Java 17, MySQL 8.x

```bash
# 1. DB 생성
mysql -u root -p -e "CREATE DATABASE meetfootball CHARACTER SET utf8mb4;"

# 2. 팀 테이블 생성
mysql -u root -p meetfootball < src/main/kotlin/com/meetfootball/sql/create-team.sql

# 3. 환경변수 설정 후 실행
DB_USERNAME=your_user DB_PASSWORD=your_password ./gradlew bootRun
```
