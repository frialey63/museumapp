package org.pjp.museum.ui.component.html5qrcode;

import java.util.function.Consumer;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.html.Div;

@CssImport("./styles/html5-qrcode.css")
//@JavaScript("http://localhost/html5-qrcode/html5-qrcode.min.js")
@JavaScript("https://unpkg.com/html5-qrcode")
public class Html5Qrcode extends Div {

    @FunctionalInterface
    public interface Scanning {
        String getUuid();
    }

    private static final long serialVersionUID = -2678129358775527301L;

    private String javascript = """
            function onScanSuccess(decodedText, decodedResult) {
                $0.$server.myScanSuccess(decodedText); html5QrcodeScanner.clear();
            };

            var html5QrcodeScanner = new Html5QrcodeScanner('reader', { supportedScanTypes: [Html5QrcodeScanType.SCAN_TYPE_CAMERA], fps: 10, qrbox: 250 });
            html5QrcodeScanner.render(onScanSuccess);
            """;

    private final Consumer<Scanning> scanConsumer;

    public Html5Qrcode(Consumer<Scanning> scanConsumer) {
        super();
        this.scanConsumer = scanConsumer;

        setId("reader");
        //getStyle().set("width", "300px");

        getElement().executeJs(javascript, this.getElement());
    }

    @ClientCallable
    private void myScanSuccess(String decodedText){
        scanConsumer.accept(new Scanning() {

            @Override
            public String getUuid() {
                return decodedText;
            }
        });
    }
}
