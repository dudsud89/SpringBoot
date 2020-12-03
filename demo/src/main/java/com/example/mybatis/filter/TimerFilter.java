package com.example.mybatis.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class TimerFilter implements Filter {

	private FilterConfig config;
	@Override
	public void destroy() {
		//3. 필터에서 나갈때 실행
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, 
			FilterChain chain) throws IOException, ServletException {
		//2. 필터작업
		long before = System.currentTimeMillis(); //시작시간
		chain.doFilter(request, response);
		long after = System.currentTimeMillis(); //종료시간	
		String uri;

		if(request instanceof HttpServletRequest){
			//downcast
			HttpServletRequest req = (HttpServletRequest)request;	
			uri = req.getRequestURI();
			//config.getServletContext().log(uri+":"+(after-before)+"ms");//로그 만드는 방법
			System.out.println(uri+":"+(after-before)+"ms");

		}

	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}	

}
