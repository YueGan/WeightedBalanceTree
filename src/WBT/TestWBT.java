package WBT;

public class TestWBT
{
  /* If no arguments, run each test sequentially, but abort at the first failure.
     If the first argument is i (a number), just run test #i.
     If there is a second argument, any failure message is suppressed. The
       exit code indicates success or failure.
   */
  public static void main(String[] args) {
    Test[] suite = {new Empty(), new NoLeft(), new NoRight(), new NoRight2(),
                    new SLSR(), new SRDL(), new DLSR(), new DRDL()
    };
    
    if (args.length == 0) {
      for (int i = 0; i < suite.length; i++) {
        System.out.println("running test " + i);
        suite[i].test();
      }
      System.out.println("finished");
    } else {
      int i = Integer.parseInt(args[0]);
      Test t = suite[i];
      if (args.length == 1) {
        t.test();
      } else {
        try {
          t.test();
        }
        catch (Exception e) {
          System.exit(1);
        }
      }
    }
  }

  public static class Fail extends RuntimeException
  {
    public Fail() { super(); };
    public Fail(WBT<?> t, Node<?> model) {
      super("\nresult:\n " +
            (t.root == null ? "null" : t.root.toString()) +
            "\ndoes not match correct answer:\n " +
            (model == null ? "null" : model.toString()) +
            "\n");
    }
  }

  private static <T> Node<T> mknode(T a, Node<T> left, Node<T> right) {
    Node<T> v = new Node<T>();
    v.key = a;
    v.num = 1;
    v.left = left;
    if (left != null) {
      v.num += left.num;
      left.parent = v;
    }
    v.right = right;
    if (right != null) {
      v.num += right.num;
      right.parent = v;
    }
    return v;
  }

  private static <K extends Comparable<K>> void same(WBT<K> t, Node<K> model) {
    if (!same(t.root, model)) {
      throw new Fail(t, model);
    }
  }

  private static <K> boolean same(Node<K> v, Node<K> model) {
    if (model == null) {
      return v == null;
    } else {
      return v != null && model.key.equals(v.key) &&
        same(model.left, v.left) && same(model.right, v.right);
    }
  }

  public static interface Test
  {
    public void test();
  }

  // corner case: insert into empty tree
  public static class Empty implements Test
  {
    public void test() {
      WBT<String> t = new WBT<String>();
      t.insert("hello");
      same(t, mknode("hello", null, null));
    }
  }

  // this insert on the left should need no rotation
  public static class NoLeft implements Test
  {
    public void test() {
      WBT<Integer> t = new WBT<Integer>();
      t.root = mknode(20, mknode(10, null, null), null);
      t.insert(5);
      same(t, mknode(20,
                     mknode(10, mknode(5, null, null), null),
                     null));
    }
  }

  // this insert on the right should need no rotation
  public static class NoRight implements Test
  {
    public void test() {
      WBT<Integer> t = new WBT<Integer>();
      t.root = mknode(10, null, mknode(20, null, null));
      t.insert(30);
      same(t, mknode(10,
                     null,
                     mknode(20, null, mknode(30, null, null))));
    }
  }

  // this insert should need no rotation
  public static class NoRight2 implements Test
  {
    public void test() {
      WBT<String> t = new WBT<String>();
      t.root = mknode("coffee",
                      mknode("cake", null, null),
                      mknode("friends",
                             mknode("couch", null, null),
                             mknode("tablet",
                                    null,
                                    mknode("wifi", null, null))));
      t.insert("mug");
      same(t, mknode("coffee",
                     mknode("cake", null, null),
                     mknode("friends",
                            mknode("couch", null, null),
                            mknode("tablet",
                                   mknode("mug", null, null),
                                   mknode("wifi", null, null))))
           );
      // indispensible for a good time in a cafe
    }
  }

  public static class SLSR implements Test
  {
    public void test() {
      WBT<Character> t = new WBT<Character>();
      t.root = mknode('B',
                      mknode('A', null, null),
                      mknode('D',
                             mknode('C', null, null),
                             mknode('Z',
                                    mknode('Y',
                                           mknode('M', null, null),
                                           null),
                                    null)));
      t.insert('X');
      same(t, mknode('D',
                     mknode('B',
                            mknode('A', null, null),
                            mknode('C', null, null)),
                     mknode('Y',
                            mknode('M',
                                   null,
                                   mknode('X', null, null)),
                            mknode('Z', null, null)))
           );
    }
  }

  public static class SRDL implements Test
  {
    public void test() {
      WBT<Character> t = new WBT<Character>();
      t.root = mknode('Y',
                      mknode('B',
                             mknode('A', null, null),
                             mknode('C',
                                    null,
                                    mknode('D',
                                           null,
                                           mknode('G', null, null)))),
                      mknode('Z', null, null));
      t.insert('F');
      same(t, mknode('D',
                     mknode('B',
                            mknode('A', null, null),
                            mknode('C', null, null)),
                     mknode('Y',
                            mknode('G',
                                   mknode('F', null, null),
                                   null),
                            mknode('Z', null, null)))
           );
    }
  }

  public static class DLSR implements Test
  {
    public void test() {
      WBT<Character> t = new WBT<Character>();
      t.root = mknode('B',
                      mknode('A', null, null),
                      mknode('D',
                             mknode('C', null, null),
                             mknode('J',
                                    mknode('E',
                                           null,
                                           mknode('F', null, null)),
                                    null)));
      t.insert('G');
      same(t, mknode('D',
                     mknode('B',
                            mknode('A', null, null),
                            mknode('C', null, null)),
                     mknode('F',
                            mknode('E', null, null),
                            mknode('J',
                                   mknode('G', null, null),
                                   null)))
           );
    }
  }

  public static class DRDL implements Test
  {
    public void test() {
      WBT<Character> t = new WBT<Character>();
      t.root = mknode('M',
                      mknode('B',
                             mknode('A', null, null),
                             mknode('C',
                                    null,
                                    mknode('H',
                                           mknode('G', null, null),
                                           null))),
                      mknode('N', null, null));
      t.insert('E');
      same(t, mknode('G',
                     mknode('B',
                            mknode('A', null, null),
                            mknode('C',
                                   null,
                                   mknode('E', null, null))),
                     mknode('M',
                            mknode('H', null, null),
                            mknode('N', null, null)))
           );
    }
  }
}
