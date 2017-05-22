package accountant.controller;

import accountant.data.Notification;
import accountant.util.SecurityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@PropertySource(value = { "classpath:application.properties" })
public abstract class BaseController {
	private boolean includeAccountData;

	public static final String PAGE_HEADER = "pageHeader";
	public static final String NOTIFICATIONS = "notifications";

	@Autowired
	protected Environment environment;

	@Autowired
//	@Qualifier("conversionServiceFactoryBean")
	protected ConversionServiceFactoryBean conversionServiceFactoryBean;

	protected ConversionService conversationService;

	protected void defaultModelInitialize(ModelAndView model, ArrayList<Notification> notifications, String pageHeader) {
		initHeader(model);
		model.addObject(NOTIFICATIONS, notifications);
		model.addObject(PAGE_HEADER, pageHeader);
	}

	private void initHeader(ModelAndView model) {
		if (SecurityHelper.getSso().equals("anonymousUser")) {
			includeAccountData = false;
		} else {
			includeAccountData = true;
		}

		model.addObject("includeAccountData", includeAccountData);

		if (includeAccountData) {
			model.addObject("userName", SecurityHelper.getSso());
		}
	}

}
