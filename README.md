API tests execution order:

1. Tests are executed with following parameters configured in Run -> Edit Configurations -> Edit Configuration templates -> JUnit:
   -Dauth.key=lXGp4CSPnzz9Zw5ykDDMEUPNUMAiLNGPG2txrdNs -Dendpoint.uri=https://api-dev.afterpay.dev/ -Drequest.uri=api/v3/validate/bank-account
2. In case if parameters not configured, tests are using variables located in com.arvato.services.rest.Constants:
   public static final String AUTH_KEY = "lXGp4CSPnzz9Zw5ykDDMEUPNUMAiLNGPG2txrdNs";
   public static final String TEST_API_ENDPOINT = "https://api-dev.afterpay.dev/";
   public static final String BANK_ACCOUNT_URI = "api/v3/validate/bank-account";