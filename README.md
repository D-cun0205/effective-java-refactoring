## Effective Java

---



# Refactoring

## 이해하기 힘든 이름(Mysterius Name)

    깔끔한 코드에서 가장 중요한 것 '좋은 이름', 어떤 역할로 어떻게 사용되는지 직관적

---

### 함수 선언 변경(Change Function Declaration)

    변경 요소: 함수 이름, 메소드 이름, 시그니처 변경 및 매개변수 추가 제거 

* 좋은 이름을 가진 함수는 함수 내부를 확인하지 않아도 어떠한 행위를 하는지 알 수 있음
* 함수에 해당 함수가 어떤 행위를 하는지 주석을 작성해보고 주석으로 이름을 만들어 본다
* 함수의 매개변수는 함수 내부의 문맥(전화번호 포매팅), 의존성 결정(지불 만기일 계산)

### 변수 이름 변경(Rename Variable)

* 더 많이 사용되는 변수일수록 그 이름이 더 중요 (람다 변수, 함수 매개변수)
* 다이나믹 타입을 지원하는 언어에서는 타입을 이름에 넣기도 함
* 여러 함수에 걸쳐 쓰이는 필드 이름에 많은 고민 필요

### 필드 이름 바꾸기(Rename Field)

* Record 자료 구조의 필드 이름은 프로그램 전반에 걸쳐 참조 됨
* record 키워드는 자바 14 버전부터 지원

## 중복 코드(Duplicated Code)

    비슷한지 동일한지 주의 깊게 봐야함, 코드의 변경은 동일한 모든 곳의 변경을 필요

---

### 함수 추출하기(Extract Function)

* 의도 와 구현 분리
* 코드가 무슨 일을 하는지 알기 위해 노력해야 한다면 코드를 함수로 분리하고 함수 이름으로 표현할 수 있다
* 한줄 짜리 메소드도 의도가 분명하다면 괜찮다
* 거대한 함수안에 주석은 추출한 함수를 찾는데 좋은 단서가 된다

### 코드 정리하기(Slide Statements)

* 관련있는 코드끼리 묶어 이해를 쉽게 한다
* 함수에서 사용할 변수 상단에 미리 정의 하지 말고 사용하는 코드 바로 위에 선언
* 관련있는 코드끼리 묶은 후 함수 추출(Extract Function) 더 깔끔하게 분리 가능

### 메소드 올리기(Pull Up Method)

* 중복 코드는 당장은 잘 동작하더라도 미래에 버그를 만들어 낼 빌미를 제공
* 여러 하위 클래스에 동일한 코드가 있는 경우 이 방법을 적용
* 비슷하지만 일부 값만 다른 경우 함수 매개변수화 적용 후 이 방법을 사용
* 하위 클래스에 있는 코드가 상위 클래스가 아닌 하위 클래스 기능에 의존하는 경우 필드 올리기 적용 후 이 방법 사용
* 두 메서드가 비슷한 절차를 따르는 경우 템플릿 메서드 패턴 적용 고려

## 긴 함수(Long Function)

    함수가 길 수록 더 이해하기 어려움
    과거에 작은 함수를 사용하는 경우 더 많은 서브루틴 호출로 인한 오버헤드 발생
    작은 함수에 좋은 이름을 사용한다면 함수의 코드를 보지 않고 이해 가능
    어떤 코드에 주석을 남기고 싶으면 대신 함수를 만들고 함수의 이름으로 의도 표현

---

### 임시 변수를 질의 함수로 바꾸기(Replace Temp with Query)

* 변수를 사용하여 반복해서 동일한 식을 계산하는 것을 피하고 이름을 사용해 의미를 표현
* 긴 함수 리팩토링시 임시 변수를 추출하여 분리하면 빼낸 함수로 전달할 매개변수를 줄일 수 있음

### 매개변수 객체 만들기(Introduce Parameter Object)

* 같은 매개변수들이 여러 메소드에 걸쳐 나타나면 묶은 자료 구조를 만들 수 있음
  * 데이터간의 관계를 보다 명시적으로 나타냄
  * 함수에 전달할 매개변수 개수를 줄임
  * 도메인을 이해하는데 중요한 역할을 하는 클래스로 발전

### 객체 통째로 넘기기(Preserve Whole Object)

* 어떤 한 레코드에서 구할 수 있는 여러 값들을 함수에 전달하는 경우 매개변수를 레코드로 교체
* 매개변수 목록 줄일 수 있다, 더 늘어날 매개변수를 미연에 방지
* 의존성을 고려하여 적용
* 메서드의 위치가 적절하지 않을 수 있다
  * 객체를 넘겼을 때 기능 편애 냄새에 해당되는 경우(자기 모듈 보다 다른 모듈에 상호작용이 잦을 때)

### 함수를 명령으로 바꾸기(Replace Function with Command)

* 함수를 독립적인 객체인 Command로 만들어 사용할 수 있다
* 커맨드 패턴 장점
  * undo 기능 만들기
  * 복잡한 기능을 구현하는데 필요한 여러 메서드를 추가할 수 있다
  * 상속이나 템플릿 활용 가능
  * 복잡한 메서드를 여러 메서드나 필드를 활용해 쪼갤 수 있다
* 대부분의 경우 커맨드 보다 함수를 사용하며 커맨드 말고 다른방법이 없는 경우 사용

### 조건문 분해하기(Decompose Conditional)

* 여러 조건에 따라 달라지는 코드를 작성하다보면 종종 긴 함수가 만들어진다
* 조건 과 액션 모두 의도를 표현해야 한다
* 기술적으로 함수 추출하기와 동일하지만 의도만 다름


    before: 분기점에서 어떤 행위를 하는지 한 줄씩 읽어가며 전체 코드를 이해해야 한다
    after: 분기내용, true, false 3줄로 어떤 행위가 발생할지 내부코드를 보지 않고 파악이 가능하다

```java
class Before {
    private Participant findParticipant(String username, List<Participant> participants) {
        Participant participant = null;
        if (participants.stream().noneMatch(p -> p.username().equals(username))) {
            participant = new Participant(username);
            participants.add(participant);
        } else {
            participant = participants.stream().filter(p -> p.username().equals(username))
                    .findFirst()
                    .orElseThrow();
        }
        return participant;
    }
}
```

```java
class After {
  private Participant findParticipant(String username, List<Participant> participants) {
    return isNewParticipant(username, participants) ?
            createNewParticipant(username, participants) :
            findExistingParticipant(username, participants);
  }

  private Participant findExistingParticipant(String username, List<Participant> participants) {
    Participant participant;
    participant = participants.stream().filter(p -> p.username().equals(username))
            .findFirst()
            .orElseThrow();
    return participant;
  }

  private Participant createNewParticipant(String username, List<Participant> participants) {
    Participant participant;
    participant = new Participant(username);
    participants.add(participant);
    return participant;
  }

  private boolean isNewParticipant(String username, List<Participant> participants) {
    return participants.stream().noneMatch(p -> p.username().equals(username));
  }
}
```

### 반복문 쪼개기(Split Loop)

* 하나의 반복문에서 여러 작업의 코드를 쉽게 찾아볼 수 있다
* 반복문을 수정할 때 여러 작업을 모두 고려하여 코딩 해야한다
* 반복문을 쪼개면 쉽게 이해하고 수정 가능하다
* 성능 문제(리팩토링 / 성능 최적화)는 별개의 작업이며 리팩토링 후 성능 최적화 시도 가능


    반복문을 작업 단위로 쪼개는 경우 작업 개수만큼 반복문을 생성하여 작업 코드를 나누어 메서드를 생성한다
    위 4번째에서 언급했듯이 성능 문제가 생길 수 있으나 리팩토링 후 성능 최적화 시도로 해결

```java
class before {
    private void print() throws IOException, InterruptedException {
        GHRepository ghRepository = getGhRepository();
    
        ExecutorService service = Executors.newFixedThreadPool(8);
        CountDownLatch latch = new CountDownLatch(totalNumberOfEvents);
    
        for (int index = 1 ; index <= totalNumberOfEvents ; index++) {
            int eventId = index;
            service.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        GHIssue issue = ghRepository.getIssue(eventId);
                        List<GHIssueComment> comments = issue.getComments();
                        Date firstCreatedAt = null;
                        Participant first = null;
            
                        for (GHIssueComment comment : comments) {
                            Participant participant = findParticipant(comment.getUserName(), participants);
                            participant.setHomeworkDone(eventId);
            
                            if (firstCreatedAt == null || comment.getCreatedAt().before(firstCreatedAt)) {
                                firstCreatedAt = comment.getCreatedAt();
                                first = participant;
                            }
                        }
            
                        firstParticipantsForEachEvent[eventId - 1] = first;
                        latch.countDown();
                    } catch (IOException e) {
                        throw new IllegalArgumentException(e);
                    }
                }
            });
        }
    
        latch.await();
        service.shutdown();
    
        new StudyPrinter(this.totalNumberOfEvents, this.participants).execute();
        printFirstParticipants();
    }
}
```

```java
class after {
    private void print() throws IOException, InterruptedException {
        GHRepository ghRepository = getGhRepository();
        checkGithubIssues(ghRepository);
        new StudyPrint(this.totalNumberOfEvents, this.participants).execute();
        printFirstParticipants();
    }
  
    private void checkGithubIssues(GHRepository ghRepository) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(8);
        CountDownLatch latch = new CountDownLatch(totalNumberOfEvents);
    
        for (int index = 1 ; index <= totalNumberOfEvents ; index++) {
            int eventId = index;
            service.execute(() -> {
                try {
                    GHIssue issue = ghRepository.getIssue(eventId);
                    List<GHIssueComment> comments = issue.getComments();
                    checkHomework(comments, eventId);
                    firstParticipantsForEachEvent[eventId - 1] = findFirst(comments);
                    latch.countDown();
                } catch (IOException e) {
                    throw new IllegalArgumentException(e);
                }
            });
        }
    
        latch.await();
        service.shutdown();
    }
  
    private Participant findFirst(List<GHIssueComment> comments) throws IOException {
        Date firstCreatedAt = null;
        Participant first = null;
        for (GHIssueComment comment : comments) {
            Participant participant = findParticipant(comment.getUserName(), participants);
            if (firstCreatedAt == null || comment.getCreatedAt().before(firstCreatedAt)) {
                firstCreatedAt = comment.getCreatedAt();
                first = participant;
            }
        }
        return first;
    }
}
```

### 조건문을 다형성으로 바꾸기(Replace Conditional with Polymorphism)

* 여러 타입에 따라 각기 다른 로직으로 처리해야 하는 경우 다형성을 적용해 조건문을 보다 명확하게 분리 가능
* 반복되는 switch 문을 각기 다른 클래스를 만들어 제거 가능
* 공통으로 사용되는 로직은 상위클래스에 두며 달라지는 부분만 하위클래스에 두어 변경점에 강조


    enum 타입별 하위 클래스를 생성하고 상위 클래스를 상속받아 execute 를 오버라이딩 하고 switch 문 작업을 타입별로 쪼갠다
    이 후 불필요한 PrinterMode enum 클래스 제거
    after class 의 checkMark 메서드는 하위 클래스에 2곳에서 사용중이므로 부모 클래스에 위치하고 하위클래스에서 슈퍼클래스 메서드에
    접근하는 구조로 중복을 제거
    Use 클래스를 보면 StudyPrinter 클래스 생성자에 type 을 넘겨 생성하던 방식에서
    타입을 제거하고 필요한 하위클래스를 직접 생성하는 구조로 변경

```java
public enum PrinterMode {
    CONSOLE, CVS, MARKDOWN
}

class before {
    public void execute() throws IOException {
        switch (printerMode) {
            case CVS -> {
                try (FileWriter fileWriter = new FileWriter("participants.cvs");
                    PrintWriter writer = new PrintWriter(fileWriter)) {
                    writer.println(cvsHeader(this.participants.size()));
                    this.participants.forEach(p -> {
                        writer.println(getCvsForParticipant(p));
                    });
                }
            }
            case CONSOLE -> {
                this.participants.forEach(p -> {
                    System.out.printf("%s %s:%s\n", p.username(), checkMark(p), p.getRate(this.totalNumberOfEvents));
                });
            }
            case MARKDOWN -> {
                try (FileWriter fileWriter = new FileWriter("participants.md");
                    PrintWriter writer = new PrintWriter(fileWriter)) {
                    writer.print(header(this.participants.size()));
                    this.participants.forEach(p -> {
                        String markdownForHomework = getMarkdownForParticipant(p);
                        writer.print(markdownForHomework);
                    });
                }
            }
        }
    }
}

class Use {
    private void print() throws IOException, InterruptedException {
        checkGithubIssues(getGhRepository());
        new StudyPrinter(this.totalNumberOfEvents, this.participants, PrinterMode.CONSOLE).execute();
    }
}
```

```java
abstract class after {
    public abstract void execute() throws IOException;

    protected String checkMark(Participant p) {
        StringBuilder line = new StringBuilder();
        for (int i = 1 ; i <= this.totalNumberOfEvents ; i++) {
            if(p.homework().containsKey(i) && p.homework().get(i)) {
                line.append("|:white_check_mark:");
            } else {
                line.append("|:x:");
            }
        }
        return line.toString();
    }
}

public class CvsPrinter extends StudyPrinter {

    public CvsPrinter(int totalNumberOfEvents, List<Participant> participants) {
        super(totalNumberOfEvents, participants);
    }
  
    @Override
    public void execute() throws IOException {
        try (FileWriter fileWriter = new FileWriter("participants.cvs");
            PrintWriter writer = new PrintWriter(fileWriter)) {
            writer.println(cvsHeader(this.participants.size()));
            this.participants.forEach(p -> {
                  writer.println(getCvsForParticipant(p));
            });
        }
    }
  
    // ... 오버라이딩한 메서드에서 필요한 메서드들 상위클래스에서 빼낸다
}

public class ConsolePrinter extends StudyPrinter {

    public ConsolePrinter(int totalNumberOfEvents, List<Participant> participants) {
        super(totalNumberOfEvents, participants);
    }
  
    @Override
    public void execute() throws IOException {
        this.participants.forEach(p -> {
            System.out.printf("%s %s:%s\n", p.username(), checkMark(p), p.getRate(this.totalNumberOfEvents));
        });
    }
}

public class MarkdownPrinter extends StudyPrinter {
  
    public MarkdownPrinter(int totalNumberOfEvents, List<Participant> participants) {
        super(totalNumberOfEvents, participants);
    }
  
    @Override
    public void execute() throws IOException {
        try (FileWriter fileWriter = new FileWriter("participants.md");
            PrintWriter writer = new PrintWriter(fileWriter)) {
            writer.print(header(this.participants.size()));
      
            this.participants.forEach(p -> {
                String markdownForHomework = getMarkdownForParticipant(p);
                writer.print(markdownForHomework);
            });
        }
    }

    // ... 오버라이딩한 메서드에서 필요한 메서드들 상위클래스에서 빼낸다
}

class Use {
    private void print() throws IOException, InterruptedException {
        checkGithubIssues(getGhRepository());
        new MarkdownPrinter(this.totalNumberOfEvents, this.participants).execute();
    }
}
```

## 긴 매개변수 목록(Long Parameter List)

    어떤 함수에 매개변수가 많을수록 함수의 역할을 이해하기 힘들다
    함수는 한가지 일을 하는지, 불필요한 매개변수는 없는지, 레코드로 뭉칠 수 있는지

---

### 매개변수를 질의 함수로 바꾸기

* 함수의 매개변수 목록은 함수의 다양성을 대변하며 짧을수록 이해하기 좋다
* 어떤 한 매개변수를 다른 매개변수를 통해 알아낼 수 있다면 중복 매개변수로 생각할 수 있다
* 매개변수에 값을 전달하는 것은 함수를 호출하는 쪽 책임, 호출하는 쪽의 책임을 줄이고 내부에서 책임지도록 노력
* 임시 변수를 질의 함수로 바꾸기, 함수 선언 변경하기 를 통한 리팩토링 적용


    discountedPrice 메서드의 매개변수 discountLevel 을 질의 함수로 변경하여
    매개변수에서 제거하고 함수를 직접 호출하도록 변경

```java
class before {

    private int quantity;

    private double itemPrice;

    public Order(int quantity, double itemPrice) {
        this.quantity = quantity;
        this.itemPrice = itemPrice;
    }

    public double finalPrice() {
        double basePrice = this.quantity * this.itemPrice;
        int discountLevel = this.quantity > 100 ? 2 : 1;
        return this.discountedPrice(basePrice, discountLevel);
    }

    private double discountedPrice(double basePrice, int discountLevel) {
        return discountLevel == 2 ? basePrice * 0.9 : basePrice * 0.95;
    }
}
```

```java
class after {
    private int quantity;
  
    private double itemPrice;
  
    public Order(int quantity, double itemPrice) {
        this.quantity = quantity;
        this.itemPrice = itemPrice;
    }
  
    public double finalPrice() {
        double basePrice = this.quantity * this.itemPrice;
        return this.discountedPrice(basePrice);
    }
  
    private int discountLevel() {
        return this.quantity > 100 ? 2 : 1;
    }
  
    private double discountedPrice(double basePrice) {
        return discountLevel() == 2 ? basePrice * 0.9 : basePrice * 0.95;
    }
}
```

### 플래그 인수 제거하기(Remove Flag Argument)

* 플래그는 보통 함수에 매개변수로 전달해서 함수 내부의 로직을 분기하는데 사용
* 플래그를 사용한 함수는 차이를 파악하기 어려움
* 조건문 분해하기(Decompose Condition)를 활용할 수 있다


    긴 함수 냄새에서 사용한 조건문 분해하기로 true/false 에 대한 작업을 분해하여 메서드를 생성
    테스트 코드에서 매개변수를 전달하여 사용하던 방식에서 플래그를 지우고 분해한 메서드를 직접 호출 

```java
class before {
    public LocalDate deliveryDate(Order order, boolean isRush) {
        if (isRush) {
            int deliveryTime = switch (order.getDeliveryState()) {
                case "WA", "CA", "OR" -> 1;
                case "TX", "NY", "FL" -> 2;
                default -> 3;
            };
            return order.getPlacedOn().plusDays(deliveryTime);
        } else {
            int deliveryTime = switch (order.getDeliveryState()) {
                case "WA", "CA" -> 2;
                case "OR", "TX", "NY" -> 3;
                default -> 4;
            };
            return order.getPlacedOn().plusDays(deliveryTime);
        }
    }
}

class Test {
    @Test
    void deliveryDate() {
        LocalDate placeOn = LocalDate.of(2022, 8, 9);
        Order orderFromWA = new Order(placeOn, "WA");
    
        Shipment shipment = new Shipment();
        assertEquals(placeOn.plusDays(1), shipment.deliveryDate(orderFromWA, true));
        assertEquals(placeOn.plusDays(2), shipment.deliveryDate(orderFromWA, false));
    }
}
```

```java
class after {
    public LocalDate regularDeliveryDate(Order order) {
        int deliveryTime = switch (order.getDeliveryState()) {
            case "WA", "CA" -> 2;
            case "OR", "TX", "NY" -> 3;
            default -> 4;
        };
        return order.getPlacedOn().plusDays(deliveryTime);
    }
  
    public LocalDate rushDeliveryDate(Order order) {
        int deliveryTime = switch (order.getDeliveryState()) {
            case "WA", "CA", "OR" -> 1;
            case "TX", "NY", "FL" -> 2;
            default -> 3;
        };
        return order.getPlacedOn().plusDays(deliveryTime);
    }   
}

class Test {
    @Test
    void deliveryDate() {
        LocalDate placeOn = LocalDate.of(2022, 8, 9);
        Order orderFromWA = new Order(placeOn, "WA");
    
        Shipment shipment = new Shipment();
        assertEquals(placeOn.plusDays(1), shipment.rushDeliveryDate(orderFromWA));
        assertEquals(placeOn.plusDays(2), shipment.regularDeliveryDate(orderFromWA));
    }
}
```

### 여러 함수를 클래스로 묶기(Combine Functions into Class)

* 비슷한 매개변수 목록을 여러 함수에서 사용하고 있다면 해당 메소드를 모아서 클래스를 만든다
* 클래스 내부로 메소드를 옮기고 데이터를 필드로 만들면 메서드에 전달해야 하는 매개변수 목록을 줄일 수 있다


    before 클래스를 확인해보면 메서드에 동일하게 자주 사용되는 매개변수가 있다
    new after 클래스를 생성하여 해당 메소드를 모아서 필드로 처리하여 매개변수를 줄이고 after 클래스는
    new after 클래스의 메서드를 호출 한다

```java
class before {
    private void print() throws IOException, InterruptedException { }

    private String getMarkdownForParticipant(String username, Map<Integer, Boolean> homework) { }

    private String checkMark(Map<Integer, Boolean> homework, int totalNumberOfParticipants) { }

    private double getRate(Map<Integer, Boolean> homework) { }

    private String header(int totalNumberOfParticipants) { }
}
```

```java
class after {
    private void print() throws IOException, InterruptedException { 
        new newAfter(매개변수).print();  
    }

    private double getRate(Map<Integer, Boolean> homework) { }
  
    private String getMarkdownForParticipant(String username, Map<Integer, Boolean> homework) { }
}

class newAfter {
    
    private int totalNumberOfParticipants;
    
    newAfter(int totalNumberOfParticipants) {
        this.totalNumberOfParticipants = totalNumberOfParticipants;
    }
    
    private void print() throws IOException, InterruptedException { }
  
    private String checkMark(Map<Integer, Boolean> homework) { }
  
    private String header() { }
}
```

## 전역 데이터(Global Data)

    아무곳에서나 변경될 수 있다는 문제
    어떤 코드로 인해 값이 변경되는지 파악하기 어려움
    클래스 변수 (필드)도 비슷한 문제를 겪음

---

### 변수 캡슐화하기(Encapsulate Variable)

* 메서드는 점진적으로 새로운 메서드로 변경할 수 있으나 데이터는 한번에 모두 변경
* 데이터 구조를 변경하는 작업을 더 수월한 메서드 구조 변경 작업으로 대체 가능
* 데이터가 사용되는 범위가 크면 캡슐화가 중요
  * 값의 변경을 함수를 통해 검증 로직을 추가하거나 변경에 따른 후속 작업을 추가하는 것이 편리
* 불변 데이터인 경우 불필요


    public static 을 사용하는 변수들의 접근제한자를 private 으로 변경하고 getter, setter 생성

## 가변 데이터(Mutable Data)

    데이터를 변경하다보면 예상치 못한 결과나 해결하기 어려운 버그 발생
    함수형 프로그래밍 언어는 데이터를 변경하지 않고 복사본을 전달하며 그 밖의 프로그래밍 언어는 데이터 변경 허용
    변경되는 데이터 사용시 발생할 수 있는 리스크를 관리할 수 있는 방법을 적용

---

### 변수 쪼개기(Split Variable)