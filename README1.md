# 실용

도메인 모델 

회원과 주문 관계 -> 회원은 여러번 주문 할 수있다. (1 : N)
주문과 상품의 관계 -> 주문할 때 여러번 상품을 선택 할 수 있다. 
반대로 상품도 여러번 주문 될 수 있다. 주문 상품이라는 모델을 만들어서 다대다 관계를 
일대다, 다대일 관계로 풀어낸다. 

데이터 중심 설계의 문제점

현재 방식은 객체 설계를 테이블 설계를 맞춘 방식, 
테이블의 외래키를 객체에 그대로 가져온다. 
객체 그래프 탐색이 불가능 
참조가 없으므로 UML도 잘 못된다.
(UML이란 ? UML 다이어그램은 통합 모델링 언어를 사용하여 시스템 상호작용, 업무흐름, 컴포넌스 관계, 시스템 구조 등 소프트 웨어 등을 그린 도면)

연관관계 매핑 기초 
객체지향 스럽게 설계를 할 수 있다. 
객체랑 관계형 DB에서의 페러다임은 다르다. 

연관관계 이해 
방향 : 단방향, 양방향 
다중성 : 다대일, 일대다, 일대일, 다대다 
연관관계 주인 : 객체 양방향 연관관계는 관리 주인이 필요 
연관관계가 필요한 이유 : 객체지향 설계의 목표는 자울적인 객체들의 협력 공동체를 만드는것 


객체를 테이블에 맞추어 데이터 중심으로 모델링하면 협력 관계를 만들 수 X 
- 테이블은 외래키로 join
- 객체는 참조를 사용해서 연관된 객체를 찾는다
- 테이블과 객체 사이네는 큰 간격이 있다. 

단반향 연관관계

<Member1>
@ManyToOne
//Member 입장 -> Many, Team 입장 -> 1
@JoinColumn(name = "TEAM_ID") //join 하는 column
private Team team; //JPA에 관계를 알려주어야 한다.

Team team = new Team();
team.setName("TEAM A");
em.persist(team);

Member1 member = new Member1();
member.setUserName("member1");
member.setTeam(team); 
//JPA가 알아서 Team에서 pk 값을 꺼내서 FK 값으로 사용 
em.persist(member);

영속성 Context에 들어가는거 확인 -> em.flush 강제로 나감 / em.clear

양방향 매핑 
Member1 <==> Team 양방향 Mapping 
양방향 관계 보다는 단방향 연관관계가 좋다. 
연관관계 주인과 mappedBy

객체와 테이블이 관계를 맺는 차이 
객체 연관관계 = 2개 (=단방향 연관관계가 2개) 
회원 -> 팀 연관관계 1개 (단방향)
팀 -> 회원 연관관계 1개 (단방향)

테이블 연관관계 = 1개
(FK 하나로 양쪽을 알 수 있다.)
회원 <-> 팀의 연관관계 1개 (양방향)

객체의 양방향 관계 
객체의 양방향 관계는 사실 양방향 관계가 아닌, 서로 다른 단반향 관계 2개 이다. 
객체를 양방향으로 참조, 단방향 연관관계를 2개를 만들어야 한다. 즉, 참조를 각가 형성 
테이블은 외래 키 하나로 두 테이블의 연관관계를 관리 
MEMBER.TEAM_ID 외래 키 하나로 양방향 연관관계를 갖는다 (양쪽을 조인)
따라서 둘 중 하나로 외래키를 관리 해야 한다. (DB 입장에서는 Team_ID (fk) 값만 update 하면 된다.)

양방향 매핑 규칙 
객체의 두 관계중 하나를 연관관계 주인으로 지정 
연관관계 주인만이 외래키를 관리 (등록, 수정)
주인이 아닌쪽은 읽기만 가능 
주인은 mappedBy 속성 X 
주안이 아니면 mappedBy 속성으로 주인을 지정 

주인의 조건 
외래키가 있는 곳을 주인으로 지정해라
(DB 입장에서는 외래키가 X = 1 / 아닌 쪽을 N)
Member에는 joinColumn -> mapping 
Team에 있는 Members -> 1 : 다 관계 && mappedBy로 관리가 된다
순수한 객체 관계를 고려한다면, 양쪽 다 입력 해주어야 한다.

정리 
단방향 매핑만으로도 이미 연관관계 매핑은 완료 
양방향 매핑은 반대 방향으로 조회(객체 그래프 탐색 기능이 추가 된 것 뿐이다.)
JPQL에서 역방향으로 탐색 할 일이 많다.
단방향 매핑을 잘 하고, 양방향은 필요시 

연관관계 주인을 정하는 기준 
비즈니스 로직을 기준으로 연관관계의 주인을 선택하면 안된다. 
연관관계의 주인은 외래키의 위치를 기준으로 정해야 한다. (성능, 운영 관점에서 더 효율적이다.)

연관관계 매핑시 고려사항 
다중성
단방향, 양방향 
연관관계 주인 

다중성
다대일 : @ManyToOne
일대다 : @OneToMany
일대일 : @OneToOne
다대다 : @ManyToMany

단방향, 양방향 
-테이블
(Member Team -> 외래 key를 양쪽에 두는 것 X, 한쪽에만 외래 key setting -> Member에서 Team join 반대로 조인 가능)
외래 키 하나로 양쪽 조인 가능 
사실 방향이라는 개념은 없음 

-객체 
참조용 필드가 있는 쪽으로만 참조가 가능
(Member에서 Team reference가 있다면 가능)
한쪽만 참조하면 단방향 
양쪽 서로 참조한다면 양방향 -> 사실 양방향 X , 객체 입장에서 참조를 보년 단방향 

연관관계 주인 
테이블은 외래키 하나를 두고 두 테이블이 연관관계를 맺는다. 
객체의 양방향 관계는 참조가 두군대 
객체 양뱡향 관계는 참조가 2군대 있다. 둘중 테이블의 외래키를 관리할 곳을 지정 
연관관계 주인: 외래키를 관리 참조 
주인의 반대편 : 외래키에 영향을 주지 않음 단순히 Read만 가능 

다대일 단방향 
DB 입장에서 보면 Team = 1 , Member = N
N쪽에 외래키를 둔다고 보면 된다. 
외래키가 있는 곳에 참조를 걸면 된다. 
가장 많이 사용하는 연관관계, 다대일의 반대는 일대 다. 

다대일 양방향 
Team에 @OneToMany 지정 -> 단순히 읽기만 가능 

외래키가 있는 쪽은 연관관계 주인, 양쪽을 서로 참조 하도록 개발 

일대 다 
1 쪽에 외래키 설정 
권장하는 않는 방법이다 
개발자 입장에선 Team만 건드린 것 같지만 DB 입장에선 insert 되었는데 update? 
만약 이 경우 다대일이라면 -> 양바향을 추가 
테이블 일대다 관계는 항상 다쪽에 외래키가 있다
객체와 테이블의 차이가 있어 반대편 테이블의 외래키를 관리하는 특이한 구조 
객체는 1쪽에 외래키, 테이블쪽에서는 N 쪽에 외래키 
따라서 @JoinColumn을 꼭 사용, 사용하지 않으면 조인 테이블 방식을 사용해아 한다. 

일대 다 양방향 
이런 매핑은 없다고 보면 된다. 
읽기 전용 필드를 사용해서 양방향 처럼 사용 하는 방법 

일대일 
일대일 관계는 그 반대도 일대일 
주 테이블이나 대상 테이블 중 외래키 선택 가능 (Member & Team에 둘다 외래키 O)
주 table에 외래키 (Member가 주 table 이면 여기에)
대상 테이블에 외래키(반대로 Team에 외래키를 넣는다.)
외래키에 데이터베이스 UNI 제약 조건 추가 (1:1)

Member에 LockerId 들어간다, 딱 하나만 서로 갖을 수 있도록 설정 
Table만 생각하면 DB 설계상 Locker_ID 외래키를 Member vs Locker 중 어디에 넣어도 상관 X 
성능상 이점을 생각하면 Member에 넣는 것 이 이점이 있음. 
이미 Locker에 값이 있기 때문에 DB Query를 하나로ㅡ -> joinX Query 하나로 member에 값을 판단
다대일 단방향 매핑과 유사 
다대일 양방향 매핑처럼 외래키가 있는 곳이 연관관계 주인
반대편은 mappedBy

테이블에 외래키 단방향 
일대일의 경우 단방향 관계는 지원 X, 양방향만 지원 

<객체 연관관계> 
Member (읽기 전용) / Locker (연관관계 주인으로 설정 -> Mapping) & Entity에 있는 외래 key 관리 
<테이블 연관관계>
Member -> JPA 입장에선 proxy 값이 있는지 없는지 check 
Locker에 값이 있는지 없는지 
-> MemberTable만으로 확인 X , Locker을 뒤져야 한다. MemberId에 있는지 없는지 확인, where 문을 넣어서 값이 있어야 Locker 인정 
값이 있다면 proxy 없다면 null 
Locker -> Member 하나가 여러 locker O, 이때 UNIQUE 제약 조건을 alter로 변경 -> 여러개 insert

일대일 정리
주 테이블에 외래키 
주 객체가 대상 객체의 참조를 갖는 것 처럼 주 테이블에 외래 키를 두고 대상 테이블을 찾는다. 
장점 : 주 테이블만 조회해도 대상 테이블에 데이터가 있는지 확인 가능 
단점 : 값 없다면 null 

대상 테이블에 외래키 
대상 테이블에 외래키 존재 
장점 : 주 테이블과 대상 테이블을 일대일에서 일대 다로 변경 할때 테이블 구조 유지 
단점 : 프록시 한계, 지연로딩으로 설정 해도 항상 즉시 로딩

다대다
관계형 데이터 배이스는 정규화된 테이블 2개로 다대다관계를 표현 X 
연결 테이블을 추가해서 일대다, 다대일 관계로 풀어야 한다. 
객체는 컬랙션을 사용해서 객체 2개로 다대다 관계 가능 
편리해 보이지만 실무에서는 사용 X 
연결 테이블이 단순히 연결만 하고 끝내는 것이 아님 
주문 시간 수량 데이터 들어올 수 있다. 
생각지도 못한 Query가 나갈 수도 있음 

따라서 @ManyToMany -> @OneToMany @ManyToOne 사용 

N:M 관계는 1:N, N:1
테이블의 N:M 관계는 중간 테이블을 이용해서 1:N, N:1 
실전에서는 중간테이블이 단순 하지 않음 
@ManyToMany는 제약: 필드 추가 X , 엔티티 테이블 불일치 
실전에서는 @ManyToMany 사용 X 

@JoinColumn
name | 매핑할 외래 키 이름 | 필드명 + ... + 참조하는 테이블의 기본 키 컬럼명 
referencedColumnName | 외래 키가 참조하는 대상 테이블의 컬럼명 | 참조하는 테이블의 기본 키 컬럼명 
foreignKey(DDL) | 외래 키 제약 조건을 직접 지정 할 수 있다, 
                  이 속성은 테이블을 생성 할 때만 사용
unique nullable insertable
updatable columnDefinition table | @Column의 속성과 같다 

@ManyToOne
optional | false로 설정하면 연관된 엔티티가 항상 있어야 한다 | TRUE
fetch | 글로벌 페치 전략을 설정한다 | @ManyToOne = fetchType.EAGER, @OneToMany = fetchType.LAZY
cascade | 영속성 전이 기능을 사용 
targetEntity | 연관된 엔티티의 타입 정보를 설정한다. 
               이 기능은 거의 사용 X , 컬렉션을 사용해도 제네릭으로 타입 정보를 알 수 있다.

@OneToMany
mappedBy | 연관관계 주인 필드를 선택 
fetch | 글로벌 페치 전략 |  @ManyToOne = fetchType.EAGER, @OneToMany = fetchType.LAZY
cascade | 영속성 전이 기능을 사용 

고급 매핑 
상속관계 매핑 
관계형 DB는 상속관계 X 
슈퍼타입 서브타입 관계라는 모델링 기법이 객체 상속과 유사 
상속관계 매핑 : 객체의 상속과 구조와 DB의 슈퍼타입 서브타입 관계를 매핑 

상속관계 매핑
슈퍼타입 서브타입 논리 모델을 실제 물리 모델로 구현 
각각 테이블로 변환 -> 조인 전략 
통합 테이블로 변환 -> 단일 테이블 전략 
서브타입 테이블로 변환 -> 구현 클래스마다 테이블 전력 

주요 어노테이션 
@Inheritance(strategy = InheritanceType.XXX)
JOINED : 조인 전략 
SINGLE_TABLE : 단일 테이블 전략 
TABLE_PER_CLASS : 구현 클래스마다 테이블 전략 
@DiscriminatorColumn(name = "DTYPE")
-> default가 entity 이름 / 이름 넣지 않으면 DB Query 날렸을때 뭐가 들어왔는지 알 수 없음 
   Join 전략에는 discriminate column 없어도 Ok / singleTable에는 필요 
@DiscriminatorValue("XXX")
-> entity 명이 class 명과 동일 

조인 전략 
장점 
- 테이블 정규화 , 외래키 참조 무결성 제약조건 활용 가능 (봐야 할 키만 본다), 저장 공간 효울화
단점
- 조회시 조인을 많이 사용 -> 성능 저하 
- 조회 쿼리 복작 
- DB 저장시 INSERT SQL 2번 호출 

단일 테이블 전략 
논리 table을 하나릐 table로 병합 

장점 
- 조인이 필요 X , 조회 성능 빠름 
- 조회 쿼리 단순 
단점
- 자식 엔티티가 매핑한 컬럼은 모두 null (Data 무결성 유지 어려움)
- 단일 테이블에 모든 것을 저장 -> 테이블 커질 수 있다.
  상황에 따라서 조회 성능이 오히려 느려질 수도 있다. 

구현 클래스마다 테이블 전략 (사용 하면 안되는 전략)
- 여러 자식 테이블을 함꼐 조회 할때 성능이 느림 
- 자식 테이블을 통합해서 쿼리하기 어려움 

@MappedSuperClass
공통 매핑 정보가 필요 (중복되는 필드 값)
상속 관계 매핑X 
엔티티 X, 테이블과 매핑 X 
부모 클래스를 상속 받는 자식 클래스에 매핑 정보만 제공 
조회, 검색 불가 
직접 생성해서 사용할 일이 X -> 추상 클래스 권장 
테이블과 관계 없고, 단순히 엔티티가 공통으로 사용하는 매핑 정보를 모으는 역할 
주로 등록 수정 등록자 수정자 같은 전체 엔티티에서 공통으로 적용하는 정보를 모을때 


프록시와 연관관계 관리 
Member을 조회시 Team도 조회 ?

public void printUserAndTeam(Member1 member1) {

String userName = member1.getUserName();
System.out.println("member = " + userName);

Team team = member1.getTeam();
        System.out.println("team = " + team);
    }
Member & Team을 찾는다. Query DB가 Member을 찾아올 떄 한번에 찾아온다 

Member1 findByReference = em.getReference(Member1.class, member1.getId());
System.out.println("findByReference.getId() = " + findByReference.getId());
System.out.println("findMember.getUserName() = " + findMember.getUserName());


 => getUserName은 이미 DB에 있다. reference는 가짜를 가져온다. getUserName을 호출하는 시점에 DB에 쿼리를 날린다 
id를 가져올떄 Query X, reference 찾을 때 이미 값 넣음, DB에 쿼리 안나간다. 

findByReference는 proxy 객체를 형성, userName을 가져다 쓰는 시점에 내부적으로 영속성 Context 요청,
reference가 내부에 실제 reference를 갖고 있으며, traget에 값이 있으니 초기화는 필요 없다.


프록시 기초 
em.find VS em.getReference()
- em.getReference() -> 참조값 가져오는 것, DB 죄회를 미루는 가짜 프록시 엔티티 객체를 조회 (DB에 쿼리가 안나가는데 객체가 조회)

client - em.getReference() -> proxy (가짜 entity 객체)
                              Entity target = null 
                              getId()
                              getName()
    -> 껍대기는 동일, 단 내용이 비어있다. (껍대기에 id 값만 갖고 있다. )

- em.find() -> DB를 통해서 실제 엔티티 객체 조회 

프록시 특징 
실제 클래스를 상속 받아서 만들어 진다. 
실제 클래스와 겉 모양이 다르다.
사용하는 입장에서는 진짜 객체인지 프록시 객체인지 구분하지 않고 사용

프록시 객체는 실제 객체의 참조를 보관 
프록시 객체를 호출하면 프록시 객체는 실제 객체의 메소드 호출

         delegate
Proxy     ----->       target
entity target          id, name 
getId                  getId()
getName                getName() 

=> 실제 target에 있는 getName 호출 

프록시 객체 초기화 
Member member = em.getReference(Member.class, "id");
member.getName();

client - getName -> MemberProxy - target.getName() -> Member
                    MemberTarget                      id, Name 
                    getId                             getId
                    getName                           getName

MemberProxy에서 초기화 요청 -> 영속성 Context -> DB 조회 -> 실제 entity 생성

- 객체를 처음 사용 할때 한번만 초기화 
- 프록시 객체를 초기화 할때 프록시 객체가 실제 엔티티로 바뀌는 것은 아니다, 초기화 되면서 프록시 객체를 통해서 실제 엔티티에 접근 가능 (교체 X)
- 프록시 객체는 원본 엔티티를 상속 받는다. 따라서 타입 체크시 주의 (비교 실패, 대신 instance of)
 => 값을 비교 해 보면, proxy가 아닌 Member & Proxy인 Member -> type 맞지 않는다. 
- 영속성 Context에 찾는 엔티티가 있다면 em.getReference()를 호출해 실제 엔티티 반환 
  처음에 proxy도 반환 O -> em.find로 해도 proxy 반환 
  이때는 같은 transaction 안에서 조회 -> 값 동일 
  - member 영속성 context 1차 cache에 올려놓음, proxy로 가져와봐야 이점 x 
  - 실제 or proxy 관계 X 
- 영속성 컨텍스트의 도움을 받을 수 없는 준영속성 상태일 때 , 프록시 초기화 하면 문제 발생 
  - em.detach() -> 영속성 context에서 관리 X/ proxy 초기화 x 
  - 영속성 context를 완전히 제거 하는 것은 아니지만, 더이상 도움을 받지 않는다. 

프록시 확인 
프록시 인스턴스의 초기화 여부 확인 
PersistenceUtilUtil.isLoaded(Object entity)

프록시 클래스 확인 방법 
entity.getClass().getName() 출력 

프록시 강제 초기화 
org.hibernate.Hibernate.initialize(entity);


즉시 로딩과 지연 로딩 
지연 로딩 LAZY을 사용해서 프록시로 조회 -> (fetch = FetchType.LAZY)
MemberClass만 DB에서 조회

지연로딩 
로딩 -> member1 - 지연로딩 (lazy) -> team1 (프록시 team1 엔티티)

지연로딩 Lazy을 사용해서 프록시로 조회 
Member - lazy -> team 
Member member = em.find(Member.class, 1L);
lazy로 박혀 o -> 가짜 proxy 조회 

Member -> Team
Team team = member.getTeam()
team.getName -> 실제 team을 사용하는 시점에 초기화 
proxy를 가져와서 어떤 내부 객체를 touch  -> proxy 내부에 값 X = 초기화 

if Member와 Team을 자주 함께 사용 
즉시 로딩 (fetch = FetchType.EAGER)
로딩 - member1 -> 즉시로딩 -> tea1 (실제 team1 엔티티) 
= 같이 join 해서 가져온다. 
JPA 구현체는 가능하면 조인해서 SQL을 한번에 함께 조회 
1. Member을 가져올때 EAGER 걸린 애를 join 해서 한반 Query로 가져온다 
2. Member을 가져올때 Member 가져오고, EAGER로 되어 있다면, em.find시 Query를 두번 날린다. (Member & Team)

프록시와 즉시로딩 주의 
가급적 지연 로딩만 사용 (실무에서) 
즉시로딩을 사용하면 예상하지 못한 SQL이 발생 
즉시 로딩은 JPQL에서 N + 1 문제를 발생 
-> 해결방법 
    1. petch Join - 동적으로 원하는 것을 선택하고, 가져오는 것을 한번에 
    2. Entity graph
    3. batch size (1 + 1)

@ManyToOne, @OneToOne은 기본이 즉시 로딩 (lazy로 설정)
@OneToMany, @ManyToMany는 기본이 지연 로딩

지연로딩 활용 
Member Team은 자주 함께 사용 (즉시)
Member Order 가끔 사용 (지연)
Order Prodcut 자주 사용 (즉시)

조회 - em.find -> member - team -> teamA (FetchType.EAGER) = 한방 Query
                        - orders -> orderList(FetchType.LAZY) - prodcut -> 상품 A = proxy

지연 로딩 활용 실무 
무든 연관관계에 지연 로딩을 사용 
실무에서는 즉시 로딩을 사용 X 
JPQL fetch joing이나 엔티티 그래프를 활용 


영속성 전이 
CASCADE 

특정 엔티티를 영속 상태로 만들 때 연관된 엔티티도 함께 영속 상태로 만들고 싶을 때
(지연 즉시 로딩) -> 영속성 관계 X 
ex) 부모 엔티티를 저장할때 자식 엔티티도 함께 저장 

영속화 -> parent -> 영속성 전이 (Cascade.PERSIST) ->child (child1, child2)

영속성 전이 CASCADE 주의 
영속성 전이는 연관관계를 매핑하는 것과는 아무 관련 없다 
엔티타를 영속화 할때 연관된 엔티티도 함께 영속화 하려는 편리함을 제공할 뿐

CASCADE 종류 
All : 모두 적용 
PERSIS : 영속
REMOVE : 삭제 
MERGE : 병합 
REFRESH : REFRESH 

언제 사용? = 하나의 부모가 자식을 관리 할떄 (ex -> 개시판, 첨부파일, 즉 소우자가 하나이면서, lifeCycle 동일)
사용 하면 안되는 case -> 여러 곳에서 file을 관리. 여러 entity에서 관리 

고아 객체 
고아 객체 제거 
부모 엔티티와 연관관계가 끊어진 자식 엔티티를 자동으로 삭제 
orphanRemoval = true

Parent parent1 = em.find(Parent.class, id)
parent1,getChildren().remove(0)
자식 엔티티를 컬렉션에서 제거 

고아 객체 주의 
참조가 제거된 엔티티는 다른 곳에서 참조하지 않는 고아 객체로 보고 삭제 하는 기능 
참조하는 곳이 하니일때 사용 
특정 엔티티가 개인 소유 할때 사용 
@OneToOne, @OneToMany만 가능 

참고: 개념적으로 부모를 제거하면 자식은 고아. 따라서 고아 객체 제거 기능을 활성화 하면,
     부모를 제거할때 자식도 함께 제거 = CascadeType.REMOVE 처럼 동작 

영속성 전아 + 고아 객체, 생명주기 
CascadeType.All + orphanRemovel=true
스스로 생명주기를 관리하는 엔티티는 em.persist()로 영속화, em.remove()로 제거 
두 옵션을 모두 활성화 하면 부모 엔티티를 통해서 자식의 생명 주기를 관리 가능
-> 자식을 remove하지 않아도 parent remove하면 삭제 
도메인 주도 설계 (DDD)의 Aggregate Root 개념을 구현할때 유용 
-> Repository Aggregate Root를 통해 Context 나머지는 Repo를 형성 X 
   나머지는 Repo를 만들지 X 
   parent = AggregateRoot 
   child = root 
