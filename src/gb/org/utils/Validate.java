package gb.org.utils;

public class Validate {

    public void checkNumber(String telephone) throws Exception {
        if(telephone.charAt(0) != '+') {
            throw new PhoneException("Ошибка телефон начинается с плюсика");
        }
        else if(telephone.length() != 12) {
            throw new PhoneException("Длина телефона должна быть 11");
        }
    }
}
