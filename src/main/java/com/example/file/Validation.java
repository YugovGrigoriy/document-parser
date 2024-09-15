package com.example.file;

import org.springframework.stereotype.Component;

/*
класс заглушка нужен для dev
 */
@Component
public class Validation {

    public boolean validation(String[] validationArr) {

        if (validationArr.length != 5) {
            throw new IllegalArgumentException("Unexpected number of fields");
        }
        if (validationArr[0].trim().length() != 12) {

            throw new IllegalArgumentException("validation error:" + validationArr[0].length());
        }
        if (validationArr[1].trim().length() != 50) {
            throw new IllegalArgumentException("validation error");
        }
        if (validationArr[3].length() != 12) {
            throw new IllegalArgumentException("validation error");
        }
        if (validationArr[4].trim().length() != 12) {
            throw new IllegalArgumentException("validation error");
        }
        return true;
    }

//    public boolean validate2(PaymentEntryDto paymentEntryDto){
//        if(paymentEntryDto.getRecordNumber().length()!=12){
//           return false;
//        }
//        if(paymentEntryDto.getPayerINN().length()!=12){
//            return false;
//        }
//        if(paymentEntryDto.getPaymentID().length()!=50){
//            return false;
//        }
//
//    }

}
