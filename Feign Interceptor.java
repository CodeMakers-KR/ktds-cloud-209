@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}
	
	@Bean
	RequestInterceptor reqeustIntercepor() {
		return requestTemplate -> {
			ServletRequestAttributes requestAttributes = 
					(ServletRequestAttributes) RequestContextHolder
							.getRequestAttributes();

			if (requestAttributes != null) {
				HttpServletRequest request = requestAttributes.getRequest();
				String userId = request.getHeader("USER_ID");
				String email = request.getHeader("EMAIL");
				String roles = request.getHeader("ROLES");
				
				requestTemplate.header("USER_ID", userId);
				requestTemplate.header("EMAIL", email);
				requestTemplate.header("ROLES", roles);
			}

		};
	}
