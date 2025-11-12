package selectOddEven;

public class logic {
    public String selectOdd() {
        return "홀";
    }

    public String selectEven() {
        return "짝";
    }

    public boolean selectAnswer(int rNumber, String select) {
        if (select.equals("홀")) {
            if  (rNumber % 2 == 1) {
                return true;
            }
            else return false;
        }
        else if (select.equals("짝")) {
            if  (rNumber % 2 == 0) {
                return true;
            }
            else  return false;
        }
        else {
            System.out.println("Error!");
            return false;
        }
    }

    public int makeRandomNumber () {
        int rNumber = (int)(Math.random() * 10) + 1;
        return rNumber;
    }
}
