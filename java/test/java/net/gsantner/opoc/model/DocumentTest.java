/*#######################################################
 *   DocumentTest
 *
 *   Maintained by Gregor Santner, 2017-
 *   https://gsantner.net/
 *
 *   License: Apache 2.0
 *  https://github.com/gsantner/opoc/#licensing
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
#########################################################*/
package net.gsantner.markor.model;

import org.junit.Test;

import static net.gsantner.markor.util.DocumentIO.normalizeTitleForFilename;
import static org.assertj.core.api.Assertions.assertThat;

public class DocumentTest {

    @Test
    public void documentOlderVersion() {
        Document document = new Document();
        document.setTitle("Hello");
        document.forceAddNextChangeToHistory();
        document.setTitle("Hello World");
        document.forceAddNextChangeToHistory();
        document.goToEarlierVersion();
        assertThat(document.getTitle()).isEqualTo("Hello");
    }

    @Test
    public void documentNewerVersion() {
        Document document = new Document();
        document.setTitle("Hello");
        document.forceAddNextChangeToHistory();
        document.setTitle("Hello World");
        document.forceAddNextChangeToHistory();
        document.setTitle("Hello World Again");
        document.goToEarlierVersion();
        document.goToEarlierVersion();
        assertThat(document.getTitle()).isEqualTo("Hello");
        document.goToNewerVersion();
        assertThat(document.getTitle()).isEqualTo("Hello World");
        assertThat(document.canGoToNewerVersion()).isEqualTo(true);
        document.goToNewerVersion();
        assertThat(document.getTitle()).isEqualTo("Hello World Again");
        assertThat(document.canGoToNewerVersion()).isEqualTo(false);
    }

    @Test
    public void filenameNormalization() {
        assertThat(normalizeTitleForFilename(nd(null, "HelloWorld"))).isEqualTo("HelloWorld");
        assertThat(normalizeTitleForFilename(nd("HelloWorld", "text"))).isEqualTo("HelloWorld");
        assertThat(normalizeTitleForFilename(nd(null, "text\nnewline"))).isEqualTo("text");
        assertThat(normalizeTitleForFilename(nd(null, "sumtext/folder"))).isEqualTo("sumtextfolder");
        assertThat(normalizeTitleForFilename(nd(null, "## hello world"))).isEqualTo("hello world");
    }

    private Document nd(String title, String content) {
        Document document = new Document();
        if (title != null) {
            document.setTitle(title);
        }
        if (content != null) {
            document.setContent(content);
        }
        return document;
    }
}
