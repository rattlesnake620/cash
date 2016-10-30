package xiaogu.cash.util;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class ResourceUtil {
	@Autowired
	private ReloadableResourceBundleMessageSource messageSourceService;
	
	public String getMessage(String code, Object args[]){
		String originalMessage = messageSourceService.getMessage(code, args, Locale.CHINA);
		return originalMessage;
	}
}
