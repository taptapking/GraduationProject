package vn.edu.hust.formtest.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vn.edu.hust.formtest.data.Product
import vn.edu.hust.formtest.helper.getProductPrice
import vn.edu.hust.graduationproject.databinding.ProductRvItemBinding

class BestProductAdapter: RecyclerView.Adapter<BestProductAdapter.BestProductsViewHolder>() {
    inner class BestProductsViewHolder(private val binding: ProductRvItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(product: Product){
            binding.apply{
                Glide.with(itemView).load(product.images[0]).into(imgProduct)
                //imgProduct.setImageResource(R.drawable.photo_camera_interface_symbol_for_button)

                val priceAfterOffer = product.offerPercentage.getProductPrice(product.price)
                
                tvNewPrice.text = "${String.format("%,.0f",priceAfterOffer)} VND"

                if (product.offerPercentage != null)
                    tvPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

                if (product.offerPercentage == null)
                    tvNewPrice.visibility = View.INVISIBLE
                tvPrice.text = "${String.format("%,.0f",product.price)} VND"
                tvName.text = product.name
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestProductsViewHolder {
        return BestProductsViewHolder(
            ProductRvItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: BestProductsViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bind(product)

        holder.itemView.setOnClickListener {
            onClick?.invoke(product)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    var onClick: ((Product) -> Unit)? = null
}