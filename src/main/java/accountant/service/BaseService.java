package accountant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;

/**
 * @author lehr0416 on 07-Aug-16.
 */
public abstract class BaseService {
    @Autowired
//	@Qualifier("conversionServiceFactoryBean")
    private ConversionServiceFactoryBean conversionServiceFactoryBean;

    protected <T> T convert(Object src, Class<T> des) {
        ConversionService conversionService = conversionServiceFactoryBean.getObject();
        return conversionService.convert(src, des);
    }

}
