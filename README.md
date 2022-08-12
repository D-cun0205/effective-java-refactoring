## 기본형 집착(Primitive Obsession)

    어플리케이션이 다루는 도메인에 필요한 기본 타입을 만들지 않고 언어가 제공하는 기본 타입을 사용하는 경우가 많음
    기본형으로는 단위 또는 표기법을 표현하기 어려움

---

### 기본형을 객체로 바꾸기(Replace Primitive with Object)

* 개발 초기에는 기본형으로 표현한 데이터가 나중에 관련있는 다양한 기능을 필요로 하는 경우
  * 문자열로 표현하던 전화번호의 지역 코드가 필요한 경우
  * 숫자로 표현하던 온도의 단위를 변환하는 경우


    beforeOrder 클래스에서 사용하는 기본 타입의 변수를 객체로 바꾼다
    Priority 클래스를 생성하고 역할에 맞는 데이터 필드, 필요한 기능들을 추가한다
    afterOrder 클래스에서 생성자 체인(Constructor Chaining)을 활용하여 문자열을 받는 생성자 호출시
    Priority 객체를 포함하는 생성자 호출로 체이닝한다

```java
class beforeOrder {
    private String priority;
    // ...생성자, getter
}

class beforeOrderProcessor {
    public long numberOfHighPriorityOrders(List<Order> orders) {
        return orders.stream()
                .filter(o -> o.getPriority() == "high" || o.getPriority() == "rush")
                .count();
    }
}

class afterPriority {
    private String value;
    private List<String> legarValues = List.of("low", "normal", "high", "rush");
  
    public Priority(String value) {
        if (legarValues.contains(value)) {
            this.value = value;
        } else {
            throw new IllegalArgumentException("illegal value for priority");
        }
    }
  
    @Override
    public String toString() {
        return this.value;
    }
  
    private int index() {
        return this.legarValues.indexOf(this.value);
    }
  
    public boolean higherThan(Priority other) {
        return this.index() > other.index();
    }
}

class afterOrder {
    private Priority priority;
  
    public Order(String priority) {
        this(new Priority(priority));
    }
  
    public Order(Priority priority) {
        this.priority = priority;
    }
  
    public Priority getPriority() {
        return priority;
    }
}

class afterOrderProcessor {
    public long numberOfHighPriorityOrders(List<Order> orders) {
        return orders.stream()
                .filter(o -> o.getPriority().higherThan(new Priority("normal")))
                .count();
    }
}
```

### 타입 코드를 서브클래스로 바꾸기(Replace Type Code with Subclass)

* 비슷하지만 다른 것들을 표현해야 하는 경우, 문자열, 열거형, 숫자
  * 주문 타입으로 일반 주문, 빠른 주문 ...
  * 직원 타입으로 엔지니어, 매니저 ...
* 타입을 서브클래스로 바꾸는 계기
  * 조건문을 다형성으로 표현할 수 있을 때 서브클래스 생성 및 조건부 로직을 다형성으로 바꾸기 
  * 특정 타입에만 유효한 필드가 있을 때 서브클래스 생성 및 필드 내리기


    첫 번째 케이스 해당 객체를 상속받은 서브클래스로 해결이 가능한 경우
    beforeEmployee 에 있는 타입 문자열들을 각 각의 하위클래스로 생성하고 각 역할에 해당하는 타입을 리턴 

```java
class beforeEmployee {
    private String name;
    private String type;
  
    public Employee(String name, String type) {
      this.validate(type);
      this.name = name;
      this.type = type;
    }
  
    private void validate(String type) {
      List<String> legalTypes = List.of("engineer", "manager", "salesman");
      if (!legalTypes.contains(type)) {
        throw new IllegalArgumentException(type);
      }
    }
  
    // ...getType, toString
}

abstract class afterEmployee {
    private String name;
  
    public Employee(String name) {
      this.name = name;
    }
  
    public static Employee createEmployee(String name, String type) {
      return switch (type) {
        case "engineer" -> new Engineer(name);
        case "manager" -> new Manager(name);
        case "salesman" -> new Salesman(name);
        default -> throw new IllegalArgumentException(type);
      };
    }
  
    public abstract String getType();
  
    // ...toString
}

class afterEngineer {
    // ...생성자, getType => return "engineer" 
}
class afterManager {
    // ...생성자, getType => return "manager"
}
class afterSalesman {
    // ...생성자, getType => return "salesman"
}
```

    두 번째 케이스 해당 객체를 상속받은 서브클래스가 이미 있는 경우
    Type 부모클래스를 별도로 생성하여 해당 클래스를 상속받는 하위클래스들을 생성
    해당 객체에서 Type 클래스에 더 적합한 역할의 함수들은 이동

```java
class beforeEmployee {
    private String name;
    private String type;
  
    public Employee(String name, String type) {
      this.validate(type);
      this.name = name;
      this.type = type;
    }
  
    private void validate(String type) {
      List<String> legalTypes = List.of("engineer", "manager", "salesman");
      if (!legalTypes.contains(type)) {
        throw new IllegalArgumentException(type);
      }
    }
  
    public String capitalizedType() {
      return this.type.substring(0, 1).toUpperCase() + this.type.substring(1).toLowerCase();
    }
  
    // ...toString
}

class afterEmployeeType {
    public String capitalizedType() {
        return this.toString().substring(0, 1).toUpperCase() + this.toString().substring(1).toLowerCase();
    }
}
class afterEngineer {
    // ...toString "engineer"
}
class afterManager {
    // ...toString "manager"
}
class afterSalesman {
    // ...toString "salesman"
}

class afterEmployee {
    private String name;
    private EmployeeType type;
  
    public Employee(String name, String type) {
      this.name = name;
      this.type = this.employeeType(type);
    }
  
    private EmployeeType employeeType(String typeValue) {
      return switch (typeValue) {
        case "engineer" -> new Engineer();
        case "manager" -> new Manager();
        case "salesman" -> new Salesman();
        default -> throw new IllegalArgumentException(typeValue);
      };
    }
  
    public String capitalizedType() {
      return this.type.capitalizedType();
    }
  
    // ...toString
}
```
    

### 조건부 로직을 다형성으로 바꾸기(Replace Conditional with Polymorphism)

* 복잡한 조건식을 상속과 다형성을 사용해 코드를 명확하게 분리
* switch 문을 사용해서 타입에 따라 각기 다른 로직을 사용하는 코드
* 기본 동작과 특수한 기능이 섞여있는 경우에 상속구조를 만들어 기본 동작은 상위클래스 특수한 기능은 하위 클래스로 차이점 강조



```java
class before {
    
}

class after {
    
}
```