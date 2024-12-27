
package leafblower4s;

import com.sun.jna.*;
import com.sun.jna.Structure.FieldOrder;

interface LeafBlowerLibrary extends Library {

	public static class Language extends PointerType {
		public Language() {
			super();
		}

		public Language(Pointer p) {
			super(p);
		}
	}

  int hello_leaf();
  int blow_leaf(Language leaf);
  void free_leaf(Language leaf);
}
