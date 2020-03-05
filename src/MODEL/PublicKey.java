package MODEL;

public class PublicKey {
	byte[][] PublicKey = null;
	byte[] e=null;
	byte[] n=null;
	byte[] phi_n=null;
	public PublicKey(byte[] a,byte[] b,byte[] c){
		PublicKey = new byte[2][];
		e=a.clone();
		n=b.clone();
		phi_n = c.clone();
		PublicKey[0] = e;
		PublicKey[0] = n;
	}
	public byte[][] getPublicKey() {
		return PublicKey;
	}

	public void setPublicKey(byte[][] publicKey) {
		PublicKey = publicKey;
	}
	public byte[] get_E() {
		return e;
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
