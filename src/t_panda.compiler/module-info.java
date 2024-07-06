/**
 * Javaコンパイラを操作する
 * <pre>
 * <code>
 * TPCompiler compiler = new TPCompiler(ToolProvider.getSystemJavaCompiler());
 * CompileTask task = compiler. createTask();
 * final String pkgName    = "pkg.dynamic";
 * final String className  = "Sample";
 * final String fqcnSample = pkgName + "." + className;
 * final String srcSample  = String.format(
 *     """
 *     package %s;
 *
 *     public class %s implements java.util.Comparator&lt;String&gt; {
 *         &#064;Override
 *         public int compare(String o1, String o2) {
 *             return o1.compareTo(o2);
 *         }
 *     }
 *     """, pkgName, className)
 *     ;
 *
 * compiler.getVersion().ifPresent(System.out::println);
 *
 * CompileResult result = task. addCompileTarget(fqcnSample , srcSample)  // ソース指定
 *     .addOption("-parameters")                                          // オプション追加
 *     .addOption("-g:source")
 *     .run();
 * if( !result. isSuccess() ) {
 *     throw new Exception("コンパイルエラー\n" + result. getOutMessage() + "\n" + result. getErrMessage() );
 * }
 *
 * Class&lt;Comparator&lt;String&gt;&gt; comparator = result.getCompiledClassAs(fqcnSample);
 * List&lt;String&gt; strList = new ArrayList&lt;&gt;() {
 *     {
 *         add("bbb");
 *         add("aaa");
 *         add("ccc");
 *     }
 * };
 * strList.sort(comparator.getConstructor().newInstance());
 *
 * System.out.println(strList.toString());     // output [aaa, bbb, ccc]
 *
 * </code>
 * </pre>
 */
module t_panda.compiler {
    requires transitive java.compiler;
    opens t_panda.compiler;
    exports t_panda.compiler;
}
