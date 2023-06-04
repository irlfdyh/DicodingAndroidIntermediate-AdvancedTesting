package com.dicoding.newsapp.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dicoding.newsapp.data.NewsRepository
import com.dicoding.newsapp.data.Result
import com.dicoding.newsapp.data.local.entity.NewsEntity
import com.dicoding.newsapp.getOrAwaitValue
import com.dicoding.newsapp.util.DataDummy
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var newsRepository: NewsRepository
    private lateinit var newsViewModel: NewsViewModel
    private val dummyNews = DataDummy.generateDummyNewsEntity()

    @Before
    fun setUp() {
        newsViewModel = NewsViewModel(newsRepository)
    }

    @Test
    fun `when Get HeadlineNews Should Not Null and Return Success`() {
        val expectedNews = MutableLiveData<Result<List<NewsEntity>>>()
        expectedNews.value = Result.Success(dummyNews)

        `when`(newsRepository.getHeadlineNews()).thenReturn(expectedNews)

        val actualNews = newsViewModel.getHeadlineNews().getOrAwaitValue()

        Mockito.verify(newsRepository).getHeadlineNews()
        Assert.assertNotNull(actualNews)
        Assert.assertTrue(actualNews is Result.Success)
        Assert.assertEquals(dummyNews.size, (actualNews as Result.Success).data.size)
    }

    @Test
    fun `when Network Error Should Return Error`() {
        val headlineNews = MutableLiveData<Result<List<NewsEntity>>>()
        headlineNews.value = Result.Error("Error")

        `when`(newsRepository.getHeadlineNews()).thenReturn(headlineNews)

        val actualNews = newsViewModel.getHeadlineNews().getOrAwaitValue()

        Mockito.verify(newsRepository).getHeadlineNews()
        Assert.assertNotNull(actualNews)
        Assert.assertNotNull(actualNews is Result.Error)
    }

    @Test
    fun `when Get Bookmarks Should Not Null and Return Available Data`() {
        val expectedData = MutableLiveData<List<NewsEntity>>()
        expectedData.value = dummyNews

        `when`(newsRepository.getBookmarkedNews()).thenReturn(expectedData)

        val actualData = newsViewModel.getBookmarkedNews().getOrAwaitValue()

        Mockito.verify(newsRepository).getBookmarkedNews()
        Assert.assertNotNull(actualData)
        Assert.assertEquals(dummyNews.size, actualData.size)
    }

    @Test
    fun `when Get Bookmarks Should Not Null and Return Empty Data`() {
        val expectedData = MutableLiveData<List<NewsEntity>>()
        expectedData.value = emptyList()

        `when`(newsRepository.getBookmarkedNews()).thenReturn(expectedData)

        val actualData = newsViewModel.getBookmarkedNews().getOrAwaitValue()

        Mockito.verify(newsRepository).getBookmarkedNews()
        Assert.assertNotNull(actualData)
        Assert.assertEquals(0, actualData.size)
    }

}