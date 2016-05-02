package miuyongjun.twentysix.utils;

import com.squareup.otto.Bus;

/**
 * Created by miaoyongjun on 16/5/2.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class BusUtil {
    private static final Bus BUS = new Bus();

    private BusUtil() {
    }

    public static Bus getBusInstance() {
        return BUS;
    }

}
