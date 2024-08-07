package com.ata.salary.interceptor;

import com.ata.salary.constant.ValidSalarySearchCriteria;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Slf4j
@Component
public class TransformCriteriaInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            Enumeration<String> params = request.getParameterNames();
            FieldNameHttpServletRequestWrapper requestWrapper = new FieldNameHttpServletRequestWrapper(request);

            while (params.hasMoreElements()) {
                String paramName = params.nextElement();
                String validFieldName = ValidSalarySearchCriteria.getValidFieldName(paramName);
                String[] paramValues = request.getParameterValues(paramName);

                if (!ValidSalarySearchCriteria.isValidField(validFieldName)) {
                    requestWrapper.removeAttribute(paramName);

                } else if (validFieldName != null) {
                    requestWrapper.addParameter(validFieldName, paramValues);
                }

            }

            if (requestWrapper.getParameter("fields") == null) {
                requestWrapper = setDefaultParams(requestWrapper);
            }

            request.setAttribute("wrappedRequest", requestWrapper);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request.");
            return false;
        }
    }

    private FieldNameHttpServletRequestWrapper setDefaultParams(FieldNameHttpServletRequestWrapper requestWrapper) {
        String[] defaultFields = {"salary", "gender", "jobTitle"};
        requestWrapper.addParameter("fields", defaultFields);
        return requestWrapper;
    }

}
