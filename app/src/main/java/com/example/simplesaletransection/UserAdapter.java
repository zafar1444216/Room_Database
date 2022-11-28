package com.example.simplesaletransection;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder>{
//
//    private Context context;
//    private List<UserTable>userTableList;
//
//
//
//    public UserAdapter(Context context) {
//        this.context = context;
//        userTableList=new ArrayList<>();
//    }
//    public void addUser(UserTable userTable){
//        userTableList.add(userTable);
//        notifyDataSetChanged();
//    }
//
//    /**
//     * Called when RecyclerView needs a new {@link RecyclerView.ViewHolder} of the given type to represent
//     * an item.
//     * <p>
//     * This new ViewHolder should be constructed with a new View that can represent the items
//     * of the given type. You can either create a new View manually or inflate it from an XML
//     * layout file.
//     * <p>
//     * The new ViewHolder will be used to display items of the adapter using
//     * . Since it will be re-used to display
//     * different items in the data set, it is a good idea to cache references to sub views of
//     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
//     *
//     * @param parent   The ViewGroup into which the new View will be added after it is bound to
//     *                 an adapter position.
//     * @param viewType The view type of the new View.
//     * @return A new ViewHolder that holds a View of the given view type.
//     * @see #getItemViewType(int)
//     */
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row,parent,false);
//        return new MyViewHolder(view);
//    }
//
//    /**
//     * Called by RecyclerView to display the data at the specified position. This method should
//     * update the contents of the {@link RecyclerView.ViewHolder#itemView} to reflect the item at the given
//     * position.
//     * <p>
//     * Note that unlike {@link ListView}, RecyclerView will not call this method
//     * again if the position of the item changes in the data set unless the item itself is
//     * invalidated or the new position cannot be determined. For this reason, you should only
//     * use the <code>position</code> parameter while acquiring the related data item inside
//     * this method and should not keep a copy of it. If you need the position of an item later
//     * on (e.g. in a click listener), use {@link RecyclerView.ViewHolder#getAdapterPosition()} which will
//     * have the updated adapter position.
//     * <p>
//     * Override  instead if Adapter can
//     * handle efficient partial bind.
//     *
//     * @param holder   The ViewHolder which should be updated to represent the contents of the
//     *                 item at the given position in the data set.
//     * @param position The position of the item within the adapter's data set.
//     */
//    @Override
//    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        UserTable userTable=userTableList.get(position);
//        holder.emailid.setText(userTable.getUserEmail());
//        holder.username.setText(userTable.getUserName());
//        holder.password.setText(userTable.getUserPassword());
//
//    }
//
//    /**
//     * Returns the total number of items in the data set held by the adapter.
//     *
//     * @return The total number of items in this adapter.
//     */
//    @Override
//    public int getItemCount() {
//        return userTableList.size();
//    }
//
//    public class MyViewHolder extends RecyclerView.ViewHolder {
//        private TextView emailid,username,password;
//        private ImageView delete;
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//            emailid=itemView.findViewById(R.id.idTVCourseName);
//            username=itemView.findViewById(R.id.idTVCourseDuration);
//            password=itemView.findViewById(R.id.idTVCourseDescription);
//            delete=itemView.findViewById(R.id.delete);
//        }
//    }
//}
