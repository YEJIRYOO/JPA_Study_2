package jpabook.jpashop_v2.exception;

public class NotEnoughStockException extends RuntimeException{

    //생성자 필수? 모두 정확한 차이?
    public NotEnoughStockException(){
    }

    public NotEnoughStockException(String message){
        super(message);
    }

    public NotEnoughStockException(String message,Throwable cause){
        super(message,cause);
    }

    public NotEnoughStockException(Throwable cause){
        super(cause);
    }
}
