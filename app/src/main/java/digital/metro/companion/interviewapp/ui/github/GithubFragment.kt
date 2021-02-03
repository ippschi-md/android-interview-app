package digital.metro.companion.interviewapp.ui.github

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import digital.metro.companion.interviewapp.databinding.FragmentGithubBinding
import digital.metro.companion.interviewapp.ui.base.BaseFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.androidx.viewmodel.ext.android.viewModel

class GithubFragment : BaseFragment() {

    private companion object {
        private val TAG = GithubFragment::class.java.simpleName
    }

    private val viewModel: GithubViewModel by viewModel()

    private lateinit var binding: FragmentGithubBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGithubBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i(TAG, "Start loading")
        viewModel.load()
    }

    override fun bindFromViewModel() = listOf(
        viewModel.onUsersAvailable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.i(TAG, "onUsersAvailable")
                binding.textGithub.text = it[0].login
            },
        viewModel.onError
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.e(TAG, "onError")
            }
    )
}
