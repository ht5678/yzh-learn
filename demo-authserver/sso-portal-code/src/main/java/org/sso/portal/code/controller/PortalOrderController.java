package org.sso.portal.code.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ms.cloud.common.component.exception.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.sso.portal.code.config.MDA;
import org.sso.portal.code.entity.OrderInfo;
import org.sso.portal.code.entity.SysUser;
import org.sso.portal.code.entity.TokenInfo;
import org.sso.portal.code.vo.BuyVo;
import org.sso.portal.code.vo.OrderQo;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;


/**
 * 
 * @author yue
 *
 */
@Controller
public class PortalOrderController {

    @Autowired
    private RestTemplate restTemplate;
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PortalOrderController.class);
    
    

    @RequestMapping("/order/createOrder")
    @ResponseBody
    public Result<String> saveOrder(@RequestBody BuyVo buyVo, HttpServletRequest request,HttpServletResponse response) throws JsonProcessingException {

        try {
            HttpEntity httpEntity = wrapRequest(request,response,buyVo);

            ResponseEntity<Result<String>> responseEntity = restTemplate.exchange(MDA.ORDER_SERVER_CREATEORDER,HttpMethod.POST,
                                            httpEntity,new ParameterizedTypeReference<Result<String>>() {});

            return responseEntity.getBody();
        }catch (Exception e) {
        	LOGGER.warn("创建订单异常.....");
            return Result.fail();
        }


    }

    /**
     * 方法实现说明:包装请求头
     * @author:smlz
     * @param request
     * @param buyVo
     * @return: HttpEntity
     * @exception:
     * @date:2020/1/13 13:11
     */
    private HttpEntity wrapRequest(HttpServletRequest request,HttpServletResponse response,BuyVo buyVo) {

        TokenInfo tokenInfo = (TokenInfo) request.getSession().getAttribute(MDA.TOKEN_INFO_KEY);
        String accessToken = tokenInfo.getAccess_token();

        //拦截器中把accessToken存储到了请求头中
        /*String accessToken = GetTokenUtils.getAccessToken(request,response);*/

        SysUser sysUser = (SysUser) request.getSession().getAttribute(MDA.CURRENT_LOGIN_USER);

        OrderInfo orderInfoVo = createOrderInfo(buyVo,sysUser.getUsername());

        HttpEntity httpEntity = new HttpEntity(JSON.toJSONString(orderInfoVo),wrapHeader(accessToken));

        return httpEntity;

    }


    private OrderInfo createOrderInfo(BuyVo buyVo,String LoginUserName) {
        OrderInfo orderInfoVo = new OrderInfo();
        orderInfoVo.setProductNo(buyVo.getProductNo());
        orderInfoVo.setUserName(LoginUserName);
        orderInfoVo.setProductCount(buyVo.getProductCount());
//        orderInfoVo.setCreateDt(new Date());
        return orderInfoVo;
    }

    @RequestMapping("/detail/{orderNo}")
    public ModelAndView orderDetial(@PathVariable("orderNo") String orderNo, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {

        TokenInfo tokenInfo = (TokenInfo) request.getSession().getAttribute(MDA.TOKEN_INFO_KEY);
        String accessToken = tokenInfo.getAccess_token();

/*        //拦截器中把accessToken存储到了请求头中
        String accessToken = GetTokenUtils.getAccessToken(request,response);*/

        SysUser sysUser = (SysUser) request.getSession().getAttribute(MDA.CURRENT_LOGIN_USER);

        try {

            OrderQo qo = new OrderQo();
            qo.setOrderNo(orderNo);
            qo.setUserName(sysUser.getUsername());

            HttpEntity httpEntity = new HttpEntity(JSON.toJSONString(qo) ,wrapHeader(accessToken));


            Result<OrderInfo> orderInfoResult = restTemplate.exchange(MDA.ORDER_SERVER_DETAIL,HttpMethod.POST,
                    httpEntity,new ParameterizedTypeReference<Result<OrderInfo>>() {}).getBody();
            ModelAndView modelAndView = new ModelAndView("order_detail");
            modelAndView.addObject("orderInfo",orderInfoResult.getData());
            return modelAndView;
        }catch (Exception e) {
        	LOGGER.error("查询订单异常:{},{}",orderNo,e);
        }
        return null;
    }


    private HttpHeaders wrapHeader(String token) {
        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization","bearer "+token);
        return headers;
    }

}
