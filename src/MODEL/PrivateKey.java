package MODEL;

public class PrivateKey {
	byte[][] PrivateKey = null;
	byte[] d=null;
	byte[] n=null;
	byte[] phi_n=null;
	
	public PrivateKey(byte[] a,byte[] b,byte[] c){
		PrivateKey = new byte[2][];
		d=a.clone();
		n=b.clone();
		phi_n = c.clone();
		PrivateKey[0] = d;
		PrivateKey[0] = n;
	}
	public byte[][] getPrivateKey() {
		return PrivateKey;
	}

	public void setPrivateKey(byte[][] privateKey) {
		PrivateKey = privateKey;
	}
	public byte[] get_D() {
		return d;
	}
	public byte[] get_N() {
		return n;
	}
	public byte[] getPhi_n() {
		return phi_n;
	}
	public void setPhi_n(byte[] phi_n) {
		this.phi_n = phi_n;
	}
}
