package pers.jssd.elegenceservice.utils;

import org.springframework.data.domain.PageRequest;

/**
 * @author jssdjing@gmail.com
 */
public class PageUtils {
    public static PageRequest getPageRequest(int page, int size) {
        return PageRequest.of(page, size);
    }

}
