# JpaBasicReview

JPA 구동 방식 



META-INF/persistence.xml -> Persistence -> 생성 -> EntityManagerFactory
-> 생성 -> EntityManager

객체와 테이블을 생성하고 매핑하기

@Entity : JPA가 관리할 객체
@Id : 데이터베이스 pk와 매핑

EntityManagerFactory는 loading 시점 하나씩만 형성 
실제 DB의 transaction -> DB connection을 얻어서 Query를 날리가 날라간다. 
JPA에서 Data를 모두 변경하는 것은 transaction에서 이루어져야 함 


1. EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
-> DB 당 하나씩 묶어서 돌아간다.
2. EntityManager em = emf.createEntityManager();
-> 고객의 요청이 오면 entityManger 에서 작업을 해야 한다.
3. EntityTransaction tx = em.getTransaction();
-> JPA 모든 Data 변경은 transaction 내부에서 발생 

EntityManager -> 데이터를 물고 동작하기 때문에 사용을 다하면 닫아주어야 한다 

// 회원저장 

em.persis X -> java collection처럼 동작한다. 
JPA를 통해서 entity를 가져오면 JPA에서 관리 
JPA가 변경 되었는지를 transaction에서 commit, 만약 변경이 되었다면 updateQuery를 날린다.

주의 

엔티티 매니저 팩토리는 하나만 생성에서 애플리케이션 전체에서 공유 
엔티티 매니저는 쓰레드간 공유 X (쓰고 버림)
JPA의 모든 데이터 변경은 트랜잭션 안에서 실행 

//JPQL

가장 단순한 조회 방법 
EntityManger.find()
객체 그래프 탐색(a.getB().getC())

JPA를 사용하면 엔티티 객체를 중심을 개발 
문제는 검색 쿼리가 문제 (전체를 검색, 많은 양의 값)

검색을 할 때도 테이블이 아닌 엔티티 객체를 대상을 검색

모드 DB 데이터를 객체로 변환해서 검색하는 것은 불가능

애플리케이션이 필요한 데이터만 DB에서 불러오면 결국 검색 조건이 포함된 SQL이 필요
 
-> 실제 물리적인 table을 DB에 날리면 종속적 Query가 된다 
-> entity 객체를 대상으로 하는 JPQL이라는 것을 제공

JPA는 SQL을 추상화한 JPQL이라는 객체 지향 쿼리 언어 제공
SQL과 문법 유사 -> SELECT, FROM WHERE, GROUP BY, HAVING, JOIN 지원 
JPQL은 엔티티 객체르 대상으로 쿼리 
SQL은 DB TABLE을 대상으로 쿼리

테이블이 아닌 객체를 대상으로 검색하는 객체지향 쿼리 
SQL을 추상화 해서 특정 DB SQL에 의존 X 
JPQL을 한마디로 정의하면 객체지향 SQL 

<JPA에서 가장 중요한 2가지>

객체와 관계형 데이터 베이스 매핑 -> DB 설계, 객체 설계 (= 정적)

영속성 컨텍스트  (실제 JPA가 내부에서 동작)

엔티티 매니저 팩토리와 엔티티 매니저

Entity Manger Factory -> EntityManger 생성 -> Connection pool 사용 -> DB

=> EntityManger 고객이 요청 할 때 마다, Connection pool 내부적 data Connection 사용 

<영속성 Context>

JPA를 이해하는데 가장 중요한 용어 (엔티티를 영구 저장하는 환경)
EntityManager.persist(entity)

DB에 저장X -> 영속성 context를 통해서 entity 영속화 
persist Method는 DB에 저장 X -> entity를 영속성 context에 저장 
영속성 컨텍스트는 논리적 개념, 눈에 보이지 않는다. 엔티티 매니저를 통해서 영속성 컨텍스트에 접근 

J2SE 환경 
엔티티 매니저와 영속성 컨텍스트가 1:1 

J2SE, 스프링 프레임워크 같은 컨테이너 환경 
엔티티 매니저와 영속성 컨텍스트가 N : 1

<엔티티의 생명 주기>
비영속 : 영속성 컨텍스트와 전혀 관계가 없는 새로운 상태 
영속 : 영속성 컨텍스트에 관리 (persist)
준영속 : 영속성 컨텍스트에 저장되었다가 분리 
삭제 : remove

New - persist -> Managed
Detached - merge -> Managed / Detached <- detach, clear, close - Managed
Removed - persist -> Managed / Removed <- remove - Managed
DB - find, JPQL -> Managed / Managed - flush -> DB
Removed - flush -> DB

<비영속>
main1 (비영속) || 영속 컨텍스트 (entity Manger)
-> JPA와 관련 X 
Member main1 = new Member();
main1.setId("");
main1.setUserName("");


<영속>
영속 상태가 되었다 해서 DB에 쿼라가 날라가는 것이 아니다. 
trasaction에 commit 할때 DB에 Query가 날라간다. 

영속 컨텍스트 > main1 (영속 상태)

//객체를 생성한 상태 (비영속)
Member main1 = new Member();
main1.setId("");
main1.setUserName("");

EntityManager em = emf.createEntityManger();
em.getTransaction().begin(); -> 이때 쿼리가 날라간다.

//객체를 저장한 상태 (영속)
em.persist(main1); -> DB에 저장 X 

<준영속>
//회원 엔티티를 영속성 컨텍스트에서 분리, 준영속 
em.detach(main1); -> 아무런 관련 X 

//객체를 삭제한 상태 (삭제)
em.remove(main1); -> DB 삭제 

<영속성 컨텍스트의 이점>
DB와 application 사이에 중간에 무언가 O (이점 형성)
1. 1차 캐시
2. 동일성 보장
3. 트랜잭션을 지원하는 쓰기 지연 
4. 변경 감지
5. 지연 로딩 

<앤티티 조회, 1차 캐시>
//엔티티를 생성한 상태 (비영속)
Member main1 = new Member();
main1.setId(1L);
main1.setName("UserA");

//영속 상태
em.persist(main1);            
Member findMember = em.find(Member.class, "UserA"); -> 조회 

EntityManager -> DB를 search x / 1차 캐시를 search o 
find("main1) -> id를 조회 -> value member를 조회 


<데이터베이스에서 조회>
entityManger -> DB에 transaction 단위로 형성 & DB transaction 종료 될때 같이 끝냄 
=> 고객 요청 종료시 영속성 DB 삭제 

1차 캐시는 DB transaction 내부 작동
//엔티티를 생성한 상태 (비영속)
Member main1 = new Member();
main1.setId(2L);
main1.setName("UserB");

//영속 상태
em.persist(main1);            
Member findMember = em.find(Member.class, "UserA"); -> 조회 
Member findMember2 = em.find(Member.class, "UserB"); -> 조회 (= DB에는 1차 캐시 없음)
UserB에는 1차 캐시엔 없음 -> DB X -> 1차 캐시에 저장 -> UserB 반환 
조회를 했는데 DB에 select Query 나가지 X 
em.persist(main1) -> 1차 캐시 
DB에서 가져오는 것 x -> 1차 캐시 조회 
두번째 같은 값 조회 => 일치 하는 값 반환 

<영속 엔티티의 동일성 보장>
Member a = em.find(Member.class, "memberA");
Member b = em.find(Member.class, "memberA");

System.out.println(a == b); -> JPA가 영속 엔티티의 동일성 보장 (=1차 캐시가 있으면 가능)

단 transaction 내부에서만 발생 
1차 캐시로 반복 가능한 읽기(REPEATABLE READ) 등급의 트랜젝션 격리 수준을 DB가 아닌 애플리케이션 차원에서 제공 

<엔티티 등록, 트랜잭션을 지원하는 쓰기 지연>

EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
EntityManager em = emf.createEntityManager();
EntityTransaction emt = em.getTransaction(); 
//엔티티 메니저는 데이터 변경시 트랜잭션을 시작 
emt.begin(); // 트렌잭션 시작 

em.persist(memberA);
em.persist(memberB);
//여기까지 Insert SQL을 DB에 보내지 않는다.
영속성 Data에 차곡차곡 쌓인다. commit 시점에 DB Query 날라간다
(한번에 보낼 수 O) -> hibernate batch (버퍼링 같은 기능)

emt.commit();
//커밋하는 순간 DB에 INSERT SQL을 보낸다
DB에 날린다 

(영속 컨텍스트) - flush -> - commit -> DB

<엔티티 수정, 변경 감지> 
JPA는 Java collection 처럼 사용, 찾아온 뒤 DB를 변경 -> em.persistX (=update query 날라간다)

                                            <변경 감지> 
DB commit - flush -> 엔티티와 스냅샷 (최초 상태) 비교 - UPDATE SQL 생성 -> SQL UPDATE A (쓰기 지연 SQL 저장소)
-> flush -> SQL UPDATE A -> commit -> DB

<엔티티 삭제>
em.remove(memberA);

<플러시>
영속성 컨텍스트의 변경내용을 데이터베이스에 반영 -> 맞추는 작업

플러시 발생 
-> 변경 감지
-> 수정된 엔티티 쓰기 지연 SQL 저장소에 등록 
-> 쓰기 지연 SQL 저장소의 쿼리를 DB에 전송 (등록, 수정, 삭제 쿼리)

<영속성 컨텍스트를 플러시 하는 방법>
em.flush -> 직접 호출 
=> 바로 날라간다, DB에 insert Query 나가고, DB에 transaction이 commit 

트랜잭션 commit -> 플러시 자동 호출 
JPQL 쿼리 실행 -> 플러시 자동 호출 

if) flush 하면 1차 캐시 삭제 ? 
정답 : X 
1차 캐시 유지 only 쓰기 지연 SQL & 변경 감지 -> DB에 반영 

<JPQL 쿼리 실행시 플러시가 자동으로 호출되는 이유>

// 모든 main1 조회
List<Member> memberList = em.createQuery("select m from Member m", Member.class)
.getResultList();

for (Member members : memberList) {                
System.out.println("members = " + members);
}
-> DB에서 insert query  자체가 나가지 X, 잘못하면 문제가 발생한다. 

<Flush Mode option>
em.setFlushMode(FlushModeType.COMMIT)

FlushModeType.AUTO -> 커밋이나 쿼리를 실행할때 플러시 (기본 값)
FlushModeType.COMMIT -> 커밋할 때만 플러시  (JPQL 할 떄 크게 도움 안됨)

플러시는 영속성 컨텍스틀 비우지 않고, 변경내용을 DB에 동기화, 트랜잭션이라는 작업 단위가 중요 
-> commit 직전 동기화 

<준영속>
영속 -> 준영속 
영속 상태의 엔티티가 영속성 컨텍스트에서 분리 
영속성 컨텍스트가 제공하는 기능을 사용 X 

준영속 상태로 만드는 방법 
em.detach(entity) -> 틀정 엔티티만 준영속 상태로 전환 
=> JPA 에서 관리 X / transaction시 Query X 

em.clear() -> 영속성 컨텍스트를 완전히 초기화 
=> 1차 캐시 통으로 지운다 + new 객체 형성 -> 다시 쿼리 날라감
em.close() -> 영속성 컨텍스트를 종료 


<엔티티 매핑 소개>

객체와 테이블 매핑: @Entity, @Table
필드와 컬럼 매핑: @Column
기본 키 매핑: @Id
연관관계 매핑: (@ManyToOne, @JoinColumn //1:1, 1:다, 다:1, 다:다)


<객체와 테이블 매핑> 
@Entity 
@Entity가 붙은 클래스는 JPA가 관리 
JPA를 사용해서 테이블과 매핑 할 클래스는 @Entity 필수 

주의
기본 생성자 필수 (파라미터가 없는 public 또는 protected 생성자)
final 클래스, enum, interface, inner 클래스에 사용 X 
저장할 필드에 final 사용 X 


@Entity 속성 정리 
속성: name
JPA에서 사용할 엔티티 이름을 지정
기본값: 클래스 이름을 그대로 사용 (ex -> Member)
같은 클래스 이름이 없다면 가급적 기본값 사용 

@Table 
@Table은 엔티티와 매핑할 테이블 지정 

name | 매핑할 테이블 이름 | 엔티티 이름을 사용 
catalog | DB catalog 매핑 
schema | DB schema 매핑 
uniqueConstraints | DDL 생성시에 유니크 제약 조건 

DB 스키마 자동 생성
(참고 DDL : data definition Language)
- DDL을 애플리케이션 실행 시점에 자동 생성 -> create으로 DB 생성, 객체 Mapping -> table 자동 형성 
table 중심 -> 객체 중심 
- DB 방언을 이용해서 DB에 맞는 적절한 DDL 생성
- 생성된 DDL은 개발 장비에서만 사용 
- 생성된 DDL은 운영서버에서 사용하지 않거나 적절히 다듬은 후 사용 

데이터베이스 스키마 자동 생성 (지우는 속성 X)
create | 기존 테이블 삭제 후 다시 생성 (DROP + CREATE)
create-drop | create와 같으나, 종료시점에 테이블 DROP 
update | 변경부분만 반영 (운영 DB에는 사용 하면 안된다) -> drop 하고 싶지 않을 때 사용 
validate | 엔티티와 테이블이 정상 매핑되었는지 확인 
none | 사용 하지 않음 

DB 스키마 자동생성 주의 
운영 장비에는 절대 create, create-drop, update 사용 X 
alter 잘못 사용시 -> System 중단 
application loading 시점에 alter을 자동으로 해결 -> 상당히 위험 
update의 경우 잘못 사용시 alter가 발생, lock 걸림 

따라서 개발 초기 단계에는 create or update 사용 O 
테스트 서버에는 update, validate
스테이징과 운영 서버에는 validate or none 

DDL 생성 기능 
제약 조건 추가: 회원 이름은 필수, 10자 초과 X 
유니크한 제약 조건 추가 -> JPA 실행 자체에 영향X , alter table script가 실행 
DDL 생성 기능은 DDL을 자동 생성할 때만 사용, JPA의 실행 로직에는 영향 X 


필드와 컬럼 매핑 
@Column | 컬럼 매핑 
@Temporal | 날짜 타입 매핑 
@Enumerated | enum type mapping 
@Log | BLOB. CLOB 매핑 
@Transient | 특정 필드를 컬럼에 매핑하지 않음 (매핑 무시) / DB 관계 없이 사용 하고 싶을 때 

@Column 
name | 필드와 매핑할때 테이블의 컬럼 이름 | 객체의 필드 이름 
insertable 
updatable  |  등록 변경 가능 여부 | TRUE 
nullable(DDL) | null 값의 허용 여부를 설정, false로 설정하면 DDL 생성시에 not null 제약 조건이 붙는다. 
unique(DDL) | @Table의 uniqueConstraints와 같지만 한 컬럼에 간단히 유니크 제약 조건을 걸때 사용 
columnDefinition | 데이터베이스 컬럼 정보를 직접 줄 수 있다. | 필드의 자바 타입과 방언 정보를 사용  
length | 문자 길이 제약 조건, String type에만 사용 | 255
precision 
scale | BigDecimal 타입에서 사용 (BigInteger에서도 사용 가능) 
        precision은 소수점을포함한 전체 자리수를, scale은 소수의 자리수 
        double, float 타입에는 적용되지 않음. 정밀한 소수, 큰수를 다루어야 할때만 사용 

@Enumerated 
자바에서 enum 타입을 매핑할 때 사용 
주의 ! ORDINAL 사용 X 
value | EnumType.ORDINAL : enum 순서를 데이터 베이스에 저장 (default)
        EnumType.String : enum 이름을 데이터베이스에 저장 

@Temporal
날짜 타입을 매핑할때 사용 
참고: LocalDate, LocalDateTime을 사용할때는 생략 가능 
value | TemporalType.DATE : 날짜 데이터베이스 date 타입과 매핑 (2022-10-11)
        TemporalType.TIME : 시간 데이터베이스 time 타입과 매핑 (11:11:11)
        TemporalType.TIMESTAMP : 날짜와 시간, 데이터베이스 timestamp 타입과 매핑 (2022-12-11 11:11:11)

@Lob
데이터베이스 BLOB, CLOB 타입과 매핑 
@Lob에는 지정할 수 있는 속성 X 
매핑하는 필드 타입이 문자 CLOB : String, char[], java.sql.CLOB
매핑하는 필드 타입이 나머지 BLOB : byte[], java.sql.BLOB

@Transient 
필트 매핑 X 
데이터베이스에 저장X, 조회X 
주로 메모리상에서만 임시로 어떤 값을 보관하고 싶을 때 사용
@Transient 
private Integer temp;


기본 키 매핑 (D에는 기본 키 Mapping)
@Id : 직접 할당 
@GeneratedValue : 자동생성 
IDENTITY : 데이이터 베이스에 위임, MySql
SEQUENCE : 데이터베이스 시퀀스 오브젝트 사용, ORACLE / @SequenceGenerator 필요 
TABLE : 키 생성용 테이블 사용, 모든 DB에 사용 / @TableGenerator 필요 
AUTO : 방언에 따라 자동 지정, 기본 값 

IDENTITY 전략 
기본 키 생성을 데이터베이스에 위임 
id에 값을 넣으면 X -> null로 날라오면, DB에서 값을 setting 
DB에 id 값 들어가면 알수 O, 단 제약이 발생한다. em.persist 시점에 insert Query 날라간다. 
JPA 내부적으로 값을 select 가져온다 
영속성 context에 setting -> id 값 알 수 있다 .

JPA는 보통 트랜젝션 커밋 시점에 Insert SQL 실행 
Auto_Increment는 데이터베이스에 insert sql을 실행한 후 id 값을 알 수 있다.
em.persist 시점에 즉시 insert SQl 실행 하고 DB에서 식별자를 조회 

SEQUENCE 전략 
DB에 시퀀스는 유일한 값을 순서대로 생성하는 특별한 데이터베이스 오브젝트 
create sequence = sequence object를 형성 & DB에 sequence object을 통해서 값을 generating 
* 1부터 시작 -> 하나씩 증가 option 
* jpa는 main1 객체를 생성 & id 값 생성X 
* id 값 생성X -> generatedValue -> sequence 전략 
* GeneratedValue에 Mapping 된 Member_SEQ에서 값을 호출 
* em.persist 할때 항상 pk 값 있다. 
* 먼저 sequence에서 pk 가져오고, JPA가 sequence를 가져온다. 
* DB에 Member_seq값 가져오고, 값을 가져와서 em.persist할 때 DB에서 값을 얻어와 Member에 넣어준다. 
* 영속성 context 저장 

@SequenceGenerator 
name | 식벌자 생성기 이름 | 필수 
sequenceName | 데이터베이스에 등록되어 있는 시퀀스 이름 | hibernate_sequence 
initValue | DDL 생성 시에만 사용, 시퀀스 DDL을 생성할때 처음 1 시작하는 수를 지정 | 1
allocationSize | 시퀀스 한번 호출에 증가하는 수 (성능 최적화에 사용, DB 시퀀스 값이 하나씩 증가하도록 설정, 이 값을 반드시 1로 설정)
                default 값 50 
catalog, schema | 데이터베이스 catalog, schema 이름 

TABLE 전략 
키 생성 전용 테이블을 만들어서 DB 시퀀스를 흉내내는 전략 
장점 : 모든 DB에 적용 가능 
단점 : 성능 

TableGenerator 속성 
name | 식벌자 생성기 이름 | 필수 
table | 키 생성 테이블 명 | hibernate_sequences 
pkColumnName | 시퀀스 컬럼명 | sequence_name
valueColumnNa | 시퀀스 값 컬럼명 | next_val
pkColumnValue | 키로 사용할 이름 | 엔티티 이름
initialValue | 초기 값, 마지막으로 생성된 값이 기준 | 0
allocationSize | 시퀀스 한번 호출에 증가하는 수 | 50
catalog, schema | 데이터베이스 catalog, schema 이름 
unique Constraint | 유니크 제약 조건 지정 가능 

권장하는 식별자 전략 
기본 키 제약 조건 : null X, 유일, 변하면 안됨 
미래까지 이 조건을 만족하는 비즈니스 키 찾기 어려움, 대리키 사용 (비즈니스적으로 사용 되는게 X)
권장 : Long형 + 대체키 + 키 생성전략 사용 