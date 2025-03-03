/*
 * Copyright (c) 2017-2018 The Regents of the University of California
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package experiments;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import examples.xml.*;

import net.jqwik.api.*;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.DefaultModelReader;
import org.apache.maven.model.io.ModelReader;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Test;

// import javax.xml.soap.*;

public class ModelReaderTest {

    @Property(tries = 10)
	public void testWithInputStream(@ForAll InputStream in) {
        ModelReader reader = new DefaultModelReader();
        try {
            Model model = reader.read(in, null);
            Assert.assertNotNull(model);
        } catch (IOException e) {
            Assume.assumeNoException(e);
        }
    }

	// @Property(tries = 10)
    // public void testWithGenerator(@ForAll SOAPPart dom) {
	// 	System.out.println(dom);
    //     testWithInputStream(XMLDocumentUtils.documentToInputStream(dom));
    // }
	//
	// @Property
    // public void debugWithGenerator(SOAPPart dom) {
    //     System.out.println(XMLDocumentUtils.documentToString(dom));
    //     testWithGenerator(dom);
    // }

	@Property
    public void testWithString(String input) {
        testWithInputStream(new ByteArrayInputStream(input.getBytes()));
    }

    @Test
    public void testSmall() throws IOException {
        testWithString("<Y");
    }

}
