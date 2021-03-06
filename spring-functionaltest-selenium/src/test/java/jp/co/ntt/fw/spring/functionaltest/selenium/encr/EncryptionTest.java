/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.fw.spring.functionaltest.selenium.encr;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.openqa.selenium.By.id;
import static org.junit.Assume.assumeThat;
import jp.co.ntt.fw.spring.functionaltest.selenium.FunctionTestSupport;

import org.junit.Test;
import org.springframework.security.crypto.codec.Base64;

public class EncryptionTest extends FunctionTestSupport {

    /**
     * <ul>
     * <li>TextEncryptorを使用してテキストの暗号化、復号ができることを確認。</li>
     * </ul>
     */
    @Test
    public void testENCR0101001() {

        // メニュー画面の操作
        {
            webDriverOperations.click(id("encr0101001"));
        }

        // 暗号化情報入力
        {
            webDriverOperations.overrideText(id("rawText"), "John");
            webDriverOperations.click(id("encrypt"));
        }

        // 暗復号結果確認
        {
            assertThat(webDriverOperations.getText(id("decryptedText")),
                    is("John"));
        }

    }

    /**
     * <ul>
     * <li>queryableTextメソッドで同一の暗号化結果を返すTextEncryptorが生成されることを確認。</li>
     * </ul>
     */
    @Test
    public void testENCR0102001() {

        // メニュー画面の操作
        {
            webDriverOperations.click(id("encr0102001"));
        }

        // 暗号化情報入力
        {
            webDriverOperations.overrideText(id("rawText"), "John");
            webDriverOperations.click(id("encrypt"));
        }

        // 暗復号結果確認
        {
            assertThat(webDriverOperations.getText(id("encryptedText")),
                    is(webDriverOperations.getText(id("encryptedText2"))));
        }

    }

    /**
     * <ul>
     * <li>BytesEncryptorを使用してバイト配列の暗号化、復号ができることを確認。</li>
     * </ul>
     */
    @Test
    public void testENCR0103001() {

        // メニュー画面の操作
        {
            webDriverOperations.click(id("encr0103001"));
        }

        // 暗号化情報入力
        {
            webDriverOperations.overrideText(id("rawText"), "John");
            webDriverOperations.click(id("encrypt"));
        }

        // 暗復号結果確認
        {
            assertThat(webDriverOperations.getText(id("decryptedText")),
                    is("John"));
        }

    }

    /**
     * <ul>
     * <li>AES with GCMを使用してテキストの暗号化、復号ができることを確認。</li>
     * </ul>
     */
    @Test
    public void testENCR0104001() {

        assumeThat(webDriverOperations.isJvm8OrLater(), is(true));

        // メニュー画面の操作
        {
            webDriverOperations.click(id("encr0104001"));
        }

        // 暗号化情報入力
        {
            webDriverOperations.overrideText(id("rawText"), "John");
            webDriverOperations.click(id("encrypt"));
        }

        // 暗復号結果確認
        {
            assertThat(webDriverOperations.getText(id("decryptedText")),
                    is("John"));
        }

    }

    /**
     * <ul>
     * <li>AES with GCMを使用してバイト配列の暗号化、復号ができることを確認。</li>
     * </ul>
     */
    @Test
    public void testENCR0105001() {

        assumeThat(webDriverOperations.isJvm8OrLater(), is(true));

        // メニュー画面の操作
        {
            webDriverOperations.click(id("encr0105001"));
        }

        // 暗号化情報入力
        {
            webDriverOperations.overrideText(id("rawText"), "John");
            webDriverOperations.click(id("encrypt"));
        }

        // 暗復号結果確認
        {
            assertThat(webDriverOperations.getText(id("decryptedText")),
                    is("John"));
        }

    }

    /**
     * <ul>
     * <li>ByteKeyGeneratorを使用してバイト配列型の疑似乱数(鍵)が生成できることを確認。</li>
     * </ul>
     */
    @Test
    public void testENCR0201001() {

        // メニュー画面の操作
        {
            webDriverOperations.click(id("encr0201001"));
        }

        // 鍵長入力
        {
            webDriverOperations.overrideText(id("keyLength"), "16");
            webDriverOperations.click(id("generate"));
        }

        // 疑似乱数(鍵)生成結果確認
        {
            assertThat(Base64.decode(webDriverOperations.getText(
                    id("generatedKey")).getBytes()).length, is(16));
        }

    }

    /**
     * <ul>
     * <li>sharedメソッドを使用して同一の鍵を返すByteKeyGeneratorが生成されることを確認。</li>
     * </ul>
     */
    @Test
    public void testENCR0202001() {

        // メニュー画面の操作
        {
            webDriverOperations.click(id("encr0202001"));
        }

        // 鍵長入力
        {
            webDriverOperations.overrideText(id("keyLength"), "16");
            webDriverOperations.click(id("generate"));
        }

        // 疑似乱数(鍵)生成結果確認
        {
            assertThat(Base64.decode(webDriverOperations.getText(
                    id("generatedKey")).getBytes()).length, is(16));
            assertEquals(webDriverOperations.getText(id("generatedKey")),
                    webDriverOperations.getText(id("generatedKey2")));
        }

    }

    /**
     * <ul>
     * <li>StringKeyGeneratorを使用してテキスト型の疑似乱数(鍵)が生成できることを確認。</li>
     * </ul>
     */
    @Test
    public void testENCR0203001() {

        // メニュー画面の操作
        {
            webDriverOperations.click(id("encr0203001"));
        }

        {
            webDriverOperations.click(id("generate"));
        }

        // 疑似乱数(鍵)生成結果確認
        {
            assertThat(
                    webDriverOperations.getText(id("generatedKey")).length(),
                    is(8 * 2));
        }

    }

    /**
     * <ul>
     * <li>JCAで生成したキーペアを使用してJCAで暗号化、JCAで復号ができることを確認。</li>
     * </ul>
     */
    @Test
    public void testENCR0301001() {

        // メニュー画面の操作
        {
            webDriverOperations.click(id("encr0302001"));
        }

        // 暗号化情報入力
        {
            webDriverOperations.overrideText(id("rawText"), "John");
            webDriverOperations.click(id("encrypt"));
        }

        // 暗復号結果確認
        {
            assertThat(webDriverOperations.getText(id("decryptedText")),
                    is("John"));
        }

    }

    /**
     * <ul>
     * <li>OpenSSLで生成したキーペアを使用してJCAで暗号化、OpenSSLで復号できることを確認。</li>
     * </ul>
     */
    @Test
    public void testENCR0302001() {

        // メニュー画面の操作
        {
            webDriverOperations.click(id("encr0302001"));
        }

        // 暗号化情報入力
        {
            webDriverOperations.overrideText(id("rawText"), "John");
            webDriverOperations.click(id("encrypt"));
        }

        // 暗復号結果確認
        {
            assertThat(webDriverOperations.getText(id("decryptedText")),
                    is("John"));
        }

    }

    /**
     * <ul>
     * <li>OpenSSLで生成したキーペアを使用してOpenSSLで暗号化、JCAで復号できることを確認。</li>
     * </ul>
     */
    @Test
    public void testENCR0303001() {

        // メニュー画面の操作
        {
            webDriverOperations.click(id("encr0303001"));
        }

        // 暗号化情報入力
        {
            webDriverOperations.overrideText(id("rawText"), "John");
            webDriverOperations.click(id("encrypt"));
        }

        // 暗復号結果確認
        {
            assertThat(webDriverOperations.getText(id("decryptedText")),
                    is("John"));
        }

    }

    /**
     * <ul>
     * <li>ハイブリッド暗号化方式を使用してテキストの暗号化、復号ができることを確認。</li>
     * </ul>
     */
    @Test
    public void testENCR0401001() {

        // メニュー画面の操作
        {
            webDriverOperations.click(id("encr0401001"));
        }

        // 暗号化情報入力
        {
            webDriverOperations.overrideText(id("rawText"), "John");
            webDriverOperations.click(id("encrypt"));
        }

        // 暗復号結果確認
        {
            assertThat(webDriverOperations.getText(id("decryptedText")),
                    is("John"));
        }

    }

}
