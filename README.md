## 뒤엉킨 변경(Divergent Change)

    소프트웨어는 변경에 유연하게 대처할 수 있어야 한다
    하나의 모듈(함수 or 클래스)이 다양하게 변경되어야 하는 경우
    서로 다른 문제는 서로 다른 모듈에서 해결

---

### 단계 쪼개기(Split Phase)

* 서로 다른 일을 하는 코드는 각기 다른 모듈로 분리
  * 변경 시 관련있는 것만 신경쓸 수 있다
* 여러 일을 하는 함수의 처리 과정을 각기 다른 단계로 구분
* 서로 다른 데이터를 사용한다면 단계를 나누는데 중요한 단서로 적용
* 중간 데이터(intermediate Data)를 만들어 단계를 구분하고 매개변수를 줄일 수 있다


    상품에 대한 할인, 배송의 역할을 각 각의 메서드로 분리하고
    메서드에 사용되는 많은 매개변수를 중간 데이터를 만들어 모은다

```java
class before {
    public double priceOrder(Product product, int quantity, ShippingMethod shippingMethod) {
        final double basePrice = product.basePrice() * quantity;
        final double discount = Math.max(quantity - product.discountThreshold(), 0)
                * product.basePrice() * product.discountRate();
        final double shippingPerCase = (basePrice > shippingMethod.discountThreshold()) ?
                shippingMethod.discountedFee() : shippingMethod.feePerCase();
        final double shippingCost = quantity * shippingPerCase;
        final double price = basePrice - discount + shippingCost;
        return price;
    }
}

class after {
    public double priceOrder(Product product, int quantity, ShippingMethod shippingMethod) {
        final PriceData priceData = calculatePriceData(product, quantity);
        return applyShipping(priceData, shippingMethod);
    }
  
    private PriceData calculatePriceData(Product product, int quantity) {
        final double basePrice = product.basePrice() * quantity;
        final double discount = Math.max(quantity - product.discountThreshold(), 0)
                * product.basePrice() * product.discountRate();
        return new PriceData(basePrice, discount, quantity);
    }
  
    private double applyShipping(PriceData priceData, ShippingMethod shippingMethod) {
        final double shippingPerCase = (priceData.basePrice() > shippingMethod.discountThreshold()) ?
                shippingMethod.discountedFee() : shippingMethod.feePerCase();
        final double shippingCost = priceData.quantity() * shippingPerCase;
        return priceData.basePrice() - priceData.discount() + shippingCost;
    }
}

public record PriceData(double basePrice, double discount, int quantity) {}
```
    

### 함수 옮기기(Move Function)

* 모듈화가 잘 된 소프트웨어는 최소한의 지식으로 프로그램 변경 가능
* 관련있는 함수나 필드가 모여있으면 쉽게 찾고 이해할 수 있다
* 관련있는 함수나 필드는 항상 고정적인 것은 아니므로 때에 따라 옮겨야 함
  * 해당 함수가 다른 클래스에 있는 필드를 더 많이 참조하는 경우
  * 해당 함수를 다른 클래스에서도 필요로 하는 경우
* 함수를 옮기기 위한 새로운 클래스가 필요한 경우 여러 함수를 클래스로 묶기, 클래스 추출하기 사용

```java
class beforeAccount {
    private int daysOverdrawn;
      private AccountType type;
  
    public Account(int daysOverdrawn, AccountType type) {
        this.daysOverdrawn = daysOverdrawn;
        this.type = type;
    }
  
    public double getBankCharge() {
        double result = 4.5;
        if (this.daysOverdrawn() > 0) {
            result += this.overdraftCharge();
        }
        return result;
    }
  
    private int daysOverdrawn() {
        return this.daysOverdrawn;
    }
  
    private double overdraftCharge() {
        if (this.type.isPremium()) {
            final int baseCharge = 10;
            if (this.daysOverdrawn <= 7) {
                return baseCharge;
            } else {
                return baseCharge + (this.daysOverdrawn - 7) * 0.85;
            }
        } else {
            return this.daysOverdrawn * 1.75;
        }
    }
}

class beforeAccountType {
    private boolean premium;
  
    public AccountType(boolean premium) {
        this.premium = premium;
    }
  
    public boolean isPremium() {
        return this.premium;
    }
}

class afterAccount {
    private int daysOverdrawn;
    private AccountType type;
  
    public double getBankCharge() {
        double result = 4.5;
        if (this.daysOverdrawn() > 0) {
            result += this.type.overdraftCharge(this.daysOverdrawn());
        }
        return result;
    }
  
    private int daysOverdrawn() {
        return this.daysOverdrawn;
    }
}

class afterAccountType {
    private boolean premium;
  
    public AccountType(boolean premium) {
        this.premium = premium;
    }
  
    public boolean isPremium() {
        return this.premium;
    }
  
    public double overdraftCharge(int daysOverdrawn) {
        if (this.isPremium()) {
            final int baseCharge = 10;
            if (daysOverdrawn <= 7) {
                  return baseCharge;
            } else {
                  return baseCharge + (daysOverdrawn - 7) * 0.85;
            }
        } else {
            return daysOverdrawn * 1.75;
        }
    }
}
```

### 클래스 추출하기(Extract Class)

* 클래스가 다루는 책임(Responsibility)이 많을수록 클래스가 커진다
* 클래스를 쪼개는 기준
  * 데이터나 메소드 중 일부가 매우 밀접한 관련이 있는 경우
  * 일부 데이터가 대부분 같이 바뀌는 경우
  * 데이터 또는 메소드 중 일부를 삭제한 경우 어떻게 되는지
* 하위 클래스를 만들ㄷ어 책임을 분산


    

```java
class before {
    
}

class after {
    
}
```