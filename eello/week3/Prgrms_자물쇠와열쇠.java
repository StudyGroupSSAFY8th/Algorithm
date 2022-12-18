class Solution {
    
    private int originLockSize;
    private int newLockSize;
    private int keySize;
    private int paddingSize;

    public boolean solution(int[][] key, int[][] lock) {
        originLockSize = lock.length;
        keySize = key.length;

        // key 크기의 -1 만큼 lock에 padding 추가
        lock = addPadding(lock);

        for (int k = 0; k < 4; k++) { // 키를 4번 회전해서 맞출 수 있기때문에
            for (int i = 0; i < paddingSize + originLockSize; i++) {
                for (int j = 0; j < paddingSize + originLockSize; j++) {
                    if(isFit(key, lock, i, j))
                        return true;
                }
            }

            key = rotate(key);
        }

        return false;
    }

    private int[][] rotate(int[][] key) {
        int[][] temp = new int[keySize][keySize];
        for (int i = 0; i < keySize; i++) {
            for (int j = 0; j < keySize; j++) {
                temp[i][j] = key[keySize - j - 1][i];
            }
        }

        return temp;
    }

    private int[][] addPadding(int[][] lock) {
        paddingSize = keySize - 1;
        newLockSize = originLockSize + (paddingSize * 2);
        int[][] temp = new int[newLockSize][newLockSize];

        for (int y = 0; y < originLockSize; y++) {
            // 깊은 복사
            System.arraycopy(lock[y], 0, temp[y + paddingSize], paddingSize, originLockSize);
        }

        return temp;
    }

    private boolean isFit(int[][] key, int[][] lock, int y, int x) {
        // 키를 맞추기위해 자물쇠를 복사한 후
        int[][] temp = new int[newLockSize][newLockSize];
        for (int i = 0; i < newLockSize; i++) {
            for (int j = 0; j < newLockSize; j++) {
                temp[i][j] = lock[i][j];
            }
        }

        // 키를 맞춰본다.
        for (int i = y; i < y + keySize; i++) {
            for (int j = x; j < x + keySize; j++) {
                temp[i][j] += key[i - y][j - x];
            }
        }

        // 원래 자물쇠 영역에 키가 안들어가면 0 또는 중복된 곳이라면 1보다 크기 때문에
        // 자물쇠 영역이 1이 아니면 자물쇠가 맞지 않는 것이기 때문에 false 리턴
        for (int i = paddingSize; i < paddingSize + originLockSize; i++) {
            for (int j = paddingSize; j < paddingSize + originLockSize; j++) {
                if (temp[i][j] != 1)
                    return false;
            }
        }

        return true;
    }
}