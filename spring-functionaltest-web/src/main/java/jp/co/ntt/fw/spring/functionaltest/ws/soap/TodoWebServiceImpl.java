/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.fw.spring.functionaltest.ws.soap;

import java.io.InputStream;
import java.util.List;

import javax.activation.DataHandler;
import javax.inject.Inject;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.MTOM;
import javax.xml.ws.soap.SOAPBinding;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import jp.co.ntt.fw.spring.functionaltest.domain.model.Todo;
import jp.co.ntt.fw.spring.functionaltest.domain.service.soap.TodoService;
import jp.co.ntt.fw.spring.functionaltest.ws.exception.WsExceptionHandler;
import jp.co.ntt.fw.spring.functionaltest.ws.soap.TodoWebService;
import jp.co.ntt.fw.spring.functionaltest.ws.webfault.WebFaultException;

@MTOM
@WebService(portName = "TodoWebPort", serviceName = "TodoWebService", targetNamespace = "http://functionaltest.spring.fw.ntt.co.jp/todo", endpointInterface = "jp.co.ntt.fw.spring.functionaltest.ws.soap.TodoWebService")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class TodoWebServiceImpl extends SpringBeanAutowiringSupport implements
                                                                   TodoWebService {

    @Inject
    WsExceptionHandler handler;

    @Inject
    TodoService todoService;

    @Value("${soap.timeout.sleepMilliseconds}")
    private int time;

    @Override
    public List<Todo> getTodos() {
        return todoService.getTodos();
    }

    @Override
    public Todo getTodo(String todoId) throws WebFaultException {
        try {
            return todoService.getTodo(todoId);
        } catch (RuntimeException e) {
            handler.translateException(e);
        }
        return null;
    }

    @Override
    public Todo createTodo(Todo todo) throws WebFaultException {
        todo.setDescription(todo.getDescription() + " by " + getUsername());
        try {
            return todoService.createTodo(todo);
        } catch (RuntimeException e) {
            handler.translateException(e);
        }
        return null;
    }

    @Override
    public Todo updateTodo(Todo todo) throws WebFaultException {
        try {
            return todoService.updateTodo(todo);
        } catch (RuntimeException e) {
            handler.translateException(e);
        }
        return null;
    }

    @Override
    public void deleteTodo(String todoId) throws WebFaultException {
        try {
            todoService.deleteTodo(todoId);
        } catch (RuntimeException e) {
            handler.translateException(e);
        }
    }

    @Override
    public void deleteTodos() {
        todoService.deleteTodos();
    }

    @Override
    public void handlerTest() throws WebFaultException {
        handler.translateException(new NullPointerException("for handler test"));
    }

    @Override
    public void timeoutTest() throws WebFaultException {
        try {
            Thread.sleep(time);
        } catch (IllegalArgumentException e) {
            handler.translateException(e);
        } catch (InterruptedException ie) {
            handler.translateException(ie);
        }
    }

    @Override
    public boolean uploadFile(DataHandler dataHandler) throws WebFaultException {
        try (InputStream inputStream = dataHandler.getInputStream()) {
            return todoService.uploadFile(inputStream);
        } catch (Exception e) {
            handler.translateException(e);
        }
        return false;
    }

    private String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername();
            }
            return (String) principal.toString();
        }
        return null;
    }

}
