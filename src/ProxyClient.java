
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.impl.dom.factory.OMDOMFactory;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;


public class ProxyClient {

	public static void main(String[] args) throws AxisFault {
		ProxyClient obj = new ProxyClient();
		OMFactory domFactory = new OMDOMFactory();
		OMElement parent = domFactory.createOMElement("Test", null);
		String endpoint = "http://localhost:8280/services/pharmacyclaimsbatchproxy.pharmacyclaimsbatchproxyHttpSoap11Endpoint";
		System.out.println(obj.sendReceive(parent, endpoint, "mediate"));
	}
	
	public OMElement sendReceive(OMElement payload, String endPointReference, String operation)
            throws AxisFault {
        ServiceClient sender;
        Options options;
        OMElement response = null;

        try {
            sender = new ServiceClient();
            options = new Options();
            options.setTo(new EndpointReference(endPointReference));
            options.setProperty(org.apache.axis2.transport.http.HTTPConstants.CHUNKED, Boolean.FALSE);
            options.setTimeOutInMilliSeconds(45000);
            options.setAction("urn:" + operation);
            sender.setOptions(options);
            System.out.println(payload);
            response = sender.sendReceive(payload);

        } catch (AxisFault axisFault) {
            throw new AxisFault("AxisFault while getting response :" + axisFault.getMessage(), axisFault);
        }
        return response;
    }

}
