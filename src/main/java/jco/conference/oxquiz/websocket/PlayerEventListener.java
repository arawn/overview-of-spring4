package jco.conference.oxquiz.websocket;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.socket.WebSocketSession;

@Aspect
public class PlayerEventListener implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Around("execution(* org.springframework.web.socket.WebSocketHandler.afterConnectionEstablished(..))")
    public Object publishPlayerInEvent(ProceedingJoinPoint thisJoinPoint) throws Throwable {
        Object retVal = thisJoinPoint.proceed();

        WebSocketSession session = (WebSocketSession) thisJoinPoint.getArgs()[0];
        applicationContext.publishEvent(PlayerEvent.in(session));

        return retVal;
    }

    @Around("execution(* org.springframework.web.socket.WebSocketHandler.afterConnectionClosed(..))")
    public Object publishPlayerOutEvent(ProceedingJoinPoint thisJoinPoint) throws Throwable {
        Object retVal = thisJoinPoint.proceed();

        WebSocketSession session = (WebSocketSession) thisJoinPoint.getArgs()[0];
        applicationContext.publishEvent(PlayerEvent.out(session));

        return retVal;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
