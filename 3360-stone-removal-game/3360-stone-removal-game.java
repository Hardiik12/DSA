class Solution {
    public boolean canAliceWin(int n) {
        int stonesToRemove = 10;
        boolean aliceTurn = true;
        
        while (n >= stonesToRemove) {
            n -= stonesToRemove;
            stonesToRemove--;
            aliceTurn = !aliceTurn; // Switch turn
        }
        
        // If the loop ends, the current player cannot make a move.
        // If it's Bob's turn (aliceTurn == false), Bob loses -> Alice wins.
        // If it's Alice's turn (aliceTurn == true), Alice loses -> Bob wins.
        return !aliceTurn;
    }
}
