package com.example.system;

import com.google.firebase.messaging.RemoteMessage;

public class MulticastMessageTest
{
//    private static final AndroidConfig ANDROID = AndroidConfig.builder()
//            .setCollapseKey("collapseKey")
//            .build();
//    private static final ApnsConfig APNS = ApnsConfig.builder()
//            .setAps(Aps.builder()
//                    .setBadge(42)
//                    .build())
//            .build();
//    private static final WebpushConfig WEBPUSH = WebpushConfig.builder()
//            .putData("key", "value")
//            .build();
//    private static final RemoteMessage.Notification NOTIFICATION = new RemoteMessage.Notification("title", "body");
//
//    @Test
//    public void testMulticastMessage() {
//        MulticastMessage multicastMessage = MulticastMessage.builder()
//                .setAndroidConfig(ANDROID)
//                .setApnsConfig(APNS)
//                .setWebpushConfig(WEBPUSH)
//                .setNotification(NOTIFICATION)
//                .putData("key1", "value1")
//                .putAllData(ImmutableMap.of("key2", "value2"))
//                .addToken("token1")
//                .addAllTokens(ImmutableList.of("token2", "token3"))
//                .build();
//
//        List<Message> messages = multicastMessage.getMessageList();
//
//        assertEquals(3, messages.size());
//        for (int i = 0; i < 3; i++) {
//            Message message = messages.get(i);
//            assertMessage(message, "token" + (i + 1));
//        }
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void testNoTokens() {
//        MulticastMessage.builder().build();
//    }
//
//    @Test
//    public void testTooManyTokens() {
//        MulticastMessage.Builder builder = MulticastMessage.builder();
//        for (int i = 0; i < 101; i++) {
//            builder.addToken("token" + i);
//        }
//        try {
//            builder.build();
//            fail("No error thrown for more than 100 tokens");
//        } catch (IllegalArgumentException expected) {
//            // expected
//        }
//    }
//
//    @Test(expected = NullPointerException.class)
//    public void testNullToken() {
//        MulticastMessage.builder().addToken(null).build();
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void testEmptyToken() {
//        MulticastMessage.builder().addToken("").build();
//    }
//
//    private void assertMessage(Message message, String expectedToken) {
//        assertSame(ANDROID, message.getAndroidConfig());
//        assertSame(APNS, message.getApnsConfig());
//        assertSame(WEBPUSH, message.getWebpushConfig());
//        assertSame(NOTIFICATION, message.getNotification());
//        assertEquals(ImmutableMap.of("key1", "value1", "key2", "value2"), message.getData());
//        assertEquals(expectedToken, message.getToken());
//    }
}
