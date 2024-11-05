/*
 The MIT License

 Copyright (c) 2010-2021 Paul R. Holser, Jr.

 Permission is hereby granted, free of charge, to any person obtaining
 a copy of this software and associated documentation files (the
 "Software"), to deal in the Software without restriction, including
 without limitation the rights to use, copy, modify, merge, publish,
 distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to
 the following conditions:

 The above copyright notice and this permission notice shall be
 included in all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package experiments.randoopTest.junitquickcheck.crypto;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import junitquickcheck.crypto.*;

import net.jqwik.api.*;

import net.jqwik.api.randoop.*;

import org.assertj.core.api.*;

import javax.security.auth.kerberos.*;

public class SymmetricKeyCryptoPropertiesTest {
    @Property(tries = 10)
	public void decryptReversesEncrypt(
        @ForAll String plaintext,
		@ForAll @Deps(classes = {KerberosPrincipal.class}) @UseMethods(methods = "getAlgorithm")  KerberosKey key)
        throws Exception {

        SymmetricCrypto crypto = new SymmetricCrypto();

        EncryptionResult enciphered =
            crypto.encrypt(plaintext.getBytes(StandardCharsets.UTF_8), key);

        Assertions.assertThat(
            plaintext).
				  isEqualTo(new String(
 				               crypto.decrypt(enciphered, key),
							   StandardCharsets.UTF_8));
    }
}
