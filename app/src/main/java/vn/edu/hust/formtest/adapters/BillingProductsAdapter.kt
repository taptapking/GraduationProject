package vn.edu.hust.formtest.adapters

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import vn.edu.hust.formtest.data.CartProduct
import vn.edu.hust.formtest.helper.getProductPrice
import vn.edu.hust.graduationproject.R
import vn.edu.hust.graduationproject.databinding.BillingProductsRvItemBinding

class BillingProductsAdapter: RecyclerView.Adapter<BillingProductsAdapter.BillingProductsViewHolder>() {
    inner class BillingProductsViewHolder(val binding: BillingProductsRvItemBinding): ViewHolder(binding.root){
        fun bind(billingProduct: CartProduct){
            binding.apply {
                //Glide.with(itemView).load(billingProduct.product.image[0]).into(imageCartProduct)
                imageCartProduct.setImageResource(R.drawable.photo_camera_interface_symbol_for_button)
                tvProductCartName.text = billingProduct.product.name
                tvBillingProductQuantity.text = billingProduct.quantity.toString()

                val priceAfterPercentage = billingProduct.product.offerPercentage.getProductPrice(billingProduct.product.price)
                tvProductCartPrice.text = "${String.format("%.0f",priceAfterPercentage)} VND"

                imageCartProductColor.setImageDrawable(ColorDrawable(billingProduct.selectedColor?: Color.TRANSPARENT))
                tvCartProductSize.text = billingProduct.selectedSize?:"".also {
                    //imageCartProductSize.setImageDrawable(ColorDrawable(Color.TRANSPARENT))
                }
            }
        }
    }

    private val diffUtil = object : DiffUtil.ItemCallback<CartProduct>(){
        override fun areItemsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
            return oldItem.product == newItem.product
        }

        override fun areContentsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillingProductsViewHolder {
        return BillingProductsViewHolder(
            BillingProductsRvItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: BillingProductsViewHolder, position: Int) {
        val billingProduct = differ.currentList[position]

        holder.bind(billingProduct)
    }
}