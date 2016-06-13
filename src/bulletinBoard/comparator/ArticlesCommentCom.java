package bulletinBoard.comparator;

import java.util.Comparator;

import bulletinBoard.beans.UsersArticles;

public class ArticlesCommentCom implements Comparator<UsersArticles>{

	public int compare(UsersArticles a,UsersArticles b){
		int id1 = a.getId();
		int id2 = b.getId();

			if(id1 < id2){
				return 1;
			} else if(id1 == id2){
				return 0;
			} else {
				return -1;
			}
	}

}
