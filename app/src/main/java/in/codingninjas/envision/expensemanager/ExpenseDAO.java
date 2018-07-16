package in.codingninjas.envision.expensemanager;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.widget.ListView;

import java.util.List;

@Dao
public interface ExpenseDAO {


    @Insert
    void addExpenses(Expense expense);

    @Delete
    void deleteExpense(Expense expense);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateExpense(Expense expense);

    @Insert
    void addTwoExpenses(Expense expense1, Expense expense2);

    @Insert
    void addExpenses(Expense[] expenses);

    @Query("select * from expenses")
    List<Expense> getExpenses();

    @Query("select * from expenses where amount > :amountA")
    List<Expense> getExpensesMoreThan100(int amountA);

//    @Query("select * from comment where expenseId = :expenseId")
//    List<Comment> getCommentsForExpense(int expenseId);

    @Query("select commentId from expenses_comments where expenseId = :expenseId")
    int[] getCommentIdsForExpense(int expenseId);

    @Query("select * from comment where id in :ids")
    List<Comment> getCommentsFromIds(int[] ids);

}
