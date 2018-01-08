package base.springmvc;

import javax.servlet.http.HttpServletRequest;

import org.ibase4j.model.SysUser;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import base.IConstants;

public class CurrentUserArgumentResolver implements WebArgumentResolver {

    @Override
    public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) {
        Object o = UNRESOLVED;
        if (methodParameter.getParameterType() != null && methodParameter.getParameterType().equals(SysUser.class)) {
            HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
            o = request.getSession().getAttribute(IConstants.CURRENT_USER);
        }
        return o;
    }
}
