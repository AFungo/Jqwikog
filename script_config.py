import subprocess
import time

objects_amount = 10

def run_experiment(script_path, file_path, timeout=60):
    start_time = time.time()

    try:
        result = subprocess.run(
            [script_path, file_path],
            stdout=subprocess.PIPE,
            stderr=subprocess.PIPE,
            timeout=timeout,
            text=True
        )
        end_time = time.time()
        execution_time = end_time - start_time

        #tiempo | cantidad de objetos 10-100-1000 | assume | solo builder si/no | timelimit

        if result.returncode == 0:
            print(file_path, " ", execution_time, " ", objects_amount, " si ", 30)
        else:
            print(result.returncode, " fallo ", file_path)

    except subprocess.TimeoutExpired:
            print("timeout " + file_path)
            return -1

    return execution_time

experiments = ["PatriciaTrieTest.testPrefixMap", "PatriciaTrieTest.testCopy",
               "DateTest.dateAfterTest", "DateTest.changeYearTest", "DateTest.longMonthTest" ,
               "DateTest.mediumMonthTest", "DateTest.februaryMonthTest",
               "junitquickcheck.geom.PolygonPropertiesTest.convexity", "junitquickcheck.counter.CounterPropertiesTest.incrementing", "junitquickcheck.counter.CounterPropertiesTest.decrementing",
               "junitquickcheck.crypto.SymmetricKeyCryptoPropertiesTest.decryptReversesEncrypt", "junitquickcheck.dummy.AGeneratorTest.listAreCorrectlyGenerated"
               "junitquickcheck.geom.SegmentPropertiesTest.intersectionIsSymmetric", "junitquickcheck.money.DollarsAndCentsPropertiesTest.roundingDown",
               "junitquickcheck.money.DollarsAndCentsPropertiesTest.roundingUp", "junitquickcheck.tree.TreePropertyTest.deepestLeafConstrained",
               "junitquickcheck.tree.TreePropertyTest.depthConstrained", "BitSetTest.flipTest", "JgraphtTest.testPrim", "NodeCachingLinkedListTest.nclTest",
               "StreamGraphTest.testVector2DotProduct", "StreamGraphTest.testVector2Normalize", "StreamGraphTest.testWelshPowellColoring",
               "TreeTest.treeContainsTest"
               ]

if __name__ == '__main__':
    # Example usage:
    script_path = "./experiments_runner.sh"
    for e in experiments:
        run_experiment(script_path, "experiments.randoopTest." + e, timeout=10)