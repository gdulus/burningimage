/*
Copyright (c) 2010 Pawel Gdula <pawel.gdula@burningice.pl>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/
package pl.burningice.plugins.image.test

/**
 * Class provide additional methods for testing file upload
 *
 * @author pawel.gdula@burningice.pl
 */
class IntegrationTestFileUploadUtils extends FileUploadUtils {

    private static final String RESULT_DIR = './web-app/upload/'

    private static final String SOURCE_DIR = './resources/testImages/'

    private static final String WEB_APP_RESULT_DIR = './upload/'

    public String getResultDir() {
        return RESULT_DIR
    }

    public String getWebAppResultDir() {
        return WEB_APP_RESULT_DIR
    }

    public String getSourceDir() {
        return SOURCE_DIR
    }

}
