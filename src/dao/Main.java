package dao;

import java.util.Arrays;


import model.BaseCurrency;
import model.Checking;

public class Main {
    public static void main(String[] args) {
        Checking checking = new Checking("thomas_ternan", "12345", Arrays.asList(new BaseCurrency[]{}));
        //CheckingDao.getInstance().save(checking);

        checking = new Checking("ayrton_senna", "12346", Arrays.asList(new BaseCurrency[]{new BaseCurrency("USD", 1234), new BaseCurrency("GLOT", 3123)}));
        //CheckingDao.getInstance().save(checking);

        
        CheckingDao.getInstance().update(checking);
        CheckingDao.getInstance().delete(new Checking("thomas_ternan", "12345", Arrays.asList(new BaseCurrency[]{})));
    }
}
