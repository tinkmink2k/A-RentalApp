package edu.re.estate.presenters.chatbot;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import edu.re.estate.data.models.ChatBotMsg;
import edu.re.estate.databinding.ViewItemQuestionChatbotBinding;

public class ChatBotAdapter extends RecyclerView.Adapter<ChatBotAdapter.ChatBotViewHolder> {
    private List<ChatBotMsg> chatBotMsgs;
    private final OnChatBotItemClick listener;

    public ChatBotAdapter(List<ChatBotMsg> chatBotMsgs, OnChatBotItemClick listener) {
        this.chatBotMsgs = chatBotMsgs;
        this.listener = listener;
    }

    public void updateData(List<ChatBotMsg> newList) {
        this.chatBotMsgs = newList;
        notifyDataSetChanged();
    }

    public interface OnChatBotItemClick {
        void onItemClick(ChatBotMsg msg);
    }

    @NonNull
    @Override
    public ChatBotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewItemQuestionChatbotBinding binding = ViewItemQuestionChatbotBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ChatBotViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatBotViewHolder holder, int position) {
        ChatBotMsg msg = chatBotMsgs.get(position);
        holder.binding.tvContent.setText(msg.getContent());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(msg));
    }

    @Override
    public int getItemCount() { return chatBotMsgs == null ? 0 : chatBotMsgs.size(); }

    static class ChatBotViewHolder extends RecyclerView.ViewHolder {
        ViewItemQuestionChatbotBinding binding;
        ChatBotViewHolder(ViewItemQuestionChatbotBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}