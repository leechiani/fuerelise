package com.furelise.exception.handler;


import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.UnexpectedTypeException;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.furelise.common.model.ErrorMessageVO;
import com.furelise.exception.NumberOfModificationsException;
import com.furelise.exception.UnauthorizedException;

/**
 *   * 1) 藉由@ControllerAdvice註解，可將「對於控制器的全局(global)配置放在同一支程式中」
 *   * 2) 註解@ControllerAdvice的類別中，可將@ModelAttribute，@InitBinder@ExceptionHandler註解到方法上
 *   * 3) 註解@ControllerAdvice的類別，其作用，將作用於所有，有註解@RequestMapping的控制器的方法上
 * <p>
 *   * 4) @ModelAttribute：其作用是綁定鍵值到Model中，此可以讓全部的@RequestMapping都能獲得在此處所設置的鍵值(key-value)對
 *   * 5) @InitBinder：其作用是利用對WebDataBinder的設置，用於綁定前端請求參數送入到Model時的條件綁定(限制)
 *   * 6) @ExceptionHandler：其作用是進行全局(global)的異常處理。攔截錯誤信息，返回報錯的提示畫面與內容
 * 7) 每當請求通過@RequestMapping發送給控制器及其方法時，並且沒有本地定義的@ModelAttribute，@InitBinder和@ExceptionHandler時，將使用由@ControllerAdvice註釋的全局(global)配置
 *   *
 *   *
 *   *
 *  
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ErrorMessageVO handleUnauthorizedException(UnauthorizedException e) {
        return new ErrorMessageVO(e.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberOfModificationsException.class)
    public  ErrorMessageVO handleNumberOfModificationsException(NumberOfModificationsException e){
        return new ErrorMessageVO(e.getMessage());
    }
    
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnexpectedTypeException.class)
    public  ErrorMessageVO handleUnexpectedTypeException(UnexpectedTypeException e){
        return new ErrorMessageVO(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exceptionHandler(HttpServletRequest request, HttpServletResponse response,
                                                   Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>("{ \"message\": \"API is error\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorMessageVO handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    	return new ErrorMessageVO(e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(",")));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorMessageVO handleMethodArgumentNotValidException(MethodArgumentTypeMismatchException e) {
        return new ErrorMessageVO(e.getValue() + " is not found");
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchElementException.class)
    public ErrorMessageVO handleMethodArgumentNotValidException(NoSuchElementException e) {
        return new ErrorMessageVO("ID number not found");
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
        return new ResponseEntity<>("{ \"message\": \"Id is not found\"}", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e) {
        return new ResponseEntity<>("{ \"message\": \"" + e.getMessage() + "\"}", HttpStatus.BAD_REQUEST);
    }

    //佳妮加的
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e){
        return new ResponseEntity<>("{ \"message\": \"輸入格式不正確\"}", HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
    	return new ResponseEntity<>("上傳的圖片大小不可超過1MB", HttpStatus.BAD_REQUEST);
    }

//	@ModelAttribute
//	public void modelAttribute(Model model) {
//		//System.out.println("1111111111........................作用到所有@RequestMapping注解方法，在其執行之前，把值放入Model(for global model attributes addition)");
//		model.addAttribute("msg", "歡迎 to TibaMe!");
//	}
//
//
//	@InitBinder
//	public void initBinder(WebDataBinder webDataBinder) {
//		//System.out.println("2222222222........................作用到所有@RequestMapping注解方法，在其執行之前，初始化數据綁定器(for global init binding)");
//		webDataBinder.addCustomFormatter(new DateFormatter("yyyy-MM-dd"));
//	}
//
//
//	// for 所有「一般」報錯用 --> 如交由"error/error.jsp"統報錯用
//	@ExceptionHandler(value = Exception.class)
//    public ModelAndView exceptionHandler(HttpServletRequest req, Exception e) {
//		//System.out.println("3333333333........................作用到所有@RequestMapping注解的方法，在其拋出Exception異常時執行(for global exception handling)");
//		String errorMessage = e.getMessage();
//		String requestURL="error/error"; // --> 如交由"error/error.jsp"統ㄧ報錯用
//		//System.out.println("(來自GlobalExceptionHandler.java)"+"requestURL="+requestURL);
//		return new ModelAndView(requestURL, "errorMessage", "請修正以下錯誤<br><b><font color=red>(訊息統一來自GlobalExceptionHandler.java):</font color=red></b><br>"+errorMessage);
//    }
//
//
//	// for 所有「方法級別驗證」報錯用 --> 如返回"emp/select_page.jsp"報錯用
//	@ExceptionHandler(ConstraintViolationException.class)
//	public ModelAndView exceptionHandler(HttpServletRequest req, ConstraintViolationException e) {
//	    Set<ConstraintViolation<?>> violationsSet = e.getConstraintViolations();
//	    StringBuilder sb = new StringBuilder();
//	    for (ConstraintViolation<?> violation : violationsSet ) {
//	          sb.append(violation.getMessage() + "<br>");
//	    }
//	    //System.out.println("3333333333........................作用到所有@RequestMapping注解的方法，在其拋出Exception異常時執行(for global exception handling)");
//	    String errorMessage = sb.toString();
//	    String requestURL=req.getParameter("requestURL"); // --> 如返回"emp/select_page.jsp"報錯用
//	    //System.out.println("(來自GlobalExceptionHandler.java)"+"requestURL="+requestURL);
//	    return new ModelAndView(requestURL, "errorMessage", "(輸入格式不正確)請修正以下錯誤<br><b><font color=red>(訊息統一來自GlobalExceptionHandler.java):</font color=red></b><br>"+errorMessage);
//	}
//
//
//	// for 所有「上傳」報錯用 --> 如返回"upload/upload_Test.jsp"報錯用
//	@ExceptionHandler(MultipartException.class)
//    public ModelAndView exceptionHandler(MultipartException e) {
//		//System.out.println("3333333333........................作用到所有@RequestMapping注解的方法，在其拋出Exception異常時執行(for global exception handling)");
//		String errorMessage = e.getMessage();
//		String requestURL="upload/upload_Test"; // --> 如返回"upload/upload_Test.jsp"報錯用
//		//System.out.println("(來自GlobalExceptionHandler.java)"+"requestURL="+requestURL);
//		return new ModelAndView(requestURL, "errorMessage", "(上傳失敗)請修正以下錯誤<br><b><font color=red>(訊息統一來自GlobalExceptionHandler.java):</font color=red></b><br>"+errorMessage);
//    }


}
