package com.zss;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/27 10:51<br/>
 *
 * @author 16574<br />
 */
public class BCryptPasswordEncoderTest {
    @Test
    public void  testPasswordEncoder() throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode1 = bCryptPasswordEncoder.encode("12345678");
        System.out.println("encode1: " + encode1);
        String encode2 = bCryptPasswordEncoder.encode("123456");
        System.out.println("encode2: " + encode2);
        String encode3 = bCryptPasswordEncoder.encode("123456");
        System.out.println("encode3: " + encode3);
       /* encode1: $2a$10$bEEEMj8iYqWOLEtv51tLU.8iRf2KE3Q92XYhd01lWIuGZqF4E4u8O
        encode2: $2a$10$hDSYdon8apsXG2IX5LQriu3xNct.vPv9Oicg4dmslQBsx9yOWkb02
        encode3: $2a$10$9g3KzKT3ManFszfsbRFoYOk68yG7d6kLgCT8n6Qoe1X1lvqE1iQb6
*/
        boolean matches1 = bCryptPasswordEncoder.matches("123456", "$2a$10$bEEEMj8iYqWOLEtv51tLU.8iRf2KE3Q92XYhd01lWIuGZqF4E4u8O");
        System.out.println("matches1: " + matches1);
        boolean matches2 = bCryptPasswordEncoder.matches("123456", "$2a$10$hDSYdon8apsXG2IX5LQriu3xNct.vPv9Oicg4dmslQBsx9yOWkb02");
        System.out.println("matches2: " + matches2);
        boolean matches3 = bCryptPasswordEncoder.matches("123456", "$2a$10$pK6CFWA.UWlDQyX4oR4gx.nVmafK9OjevKIu2VX0cDaJUQ8gpYhz.");
        System.out.println("matches3: " + matches3);
    }
}
