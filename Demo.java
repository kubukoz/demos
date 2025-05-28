//> using dep software.amazon.smithy:smithy-syntax:1.58.0
//> using javacOptions --release 8
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.loader.IdlTokenizer;
import software.amazon.smithy.syntax.TokenTree;
import software.amazon.smithy.syntax.TreeType;

public class Demo {

  public static void dump() {
    try {
      Object model = Model
        .assembler()
        .assemble();
    } catch(Throwable e) {
      e.printStackTrace();
      throw e;
    }
  }

  public static Object format() {
    try {
      IdlTokenizer tokenizer = IdlTokenizer.create("input.smithy", "");
      TokenTree tree = TokenTree.of(tokenizer);

      return tree.zipper().getFirstChild(TreeType.SHAPE_SECTION);
    } catch(Throwable e) {
      e.printStackTrace();
      throw e;
    }
  }

  public static void main(String[] args) {
    dump();
    System.out.println("format result: " + format());
    dump();
  }

}
