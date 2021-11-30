package com.example.votedroid;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.example.votedroid.bd.BD;
import com.example.votedroid.bd.BD;

import com.example.votedroid.exceptions.MauvaisVote;
import com.example.votedroid.exceptions.MauvaiseQuestion;
import com.example.votedroid.modele.VDQuestion;
import com.example.votedroid.modele.VDVote;
import com.example.votedroid.service.ServiceImplementation;

@RunWith(AndroidJUnit4.class)
public class TestsApplication {

    private BD bd;
    private ServiceImplementation service;

    // S'exécute avant chacun des tests. Crée une BD en mémoire
    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        bd = Room.inMemoryDatabaseBuilder(context, BD.class).build();
        service = new ServiceImplementation(bd);
    }


    @Test(expected = MauvaiseQuestion.class)
    public void ajoutQuestionKOVide() throws MauvaiseQuestion {
        VDQuestion question = new VDQuestion();
        question.texteQuestion = "";
        service.creerQuestion(question);

        Assert.fail("Exception MauvaiseQuestion non lancée");
    }


    @Test(expected = MauvaiseQuestion.class)
    public void ajoutQuestionKOCourte() throws MauvaiseQuestion {
        VDQuestion question = new VDQuestion();
        question.texteQuestion = "aa";
        service.creerQuestion(question);

        Assert.fail("Exception MauvaiseQuestion non lancée");
    }


    @Test(expected = MauvaiseQuestion.class)
    public void ajoutQuestionKOLongue() throws MauvaiseQuestion {
        VDQuestion question = new VDQuestion();
        for (int i = 0 ; i < 256 ; i ++) question.texteQuestion += "aa";
        service.creerQuestion(question);

        Assert.fail("Exception MauvaiseQuestion non lancée");
    }


    @Test(expected = MauvaiseQuestion.class)
    public void ajoutQuestionKOIDFixe() throws MauvaiseQuestion {
        VDQuestion question = new VDQuestion();
        question.texteQuestion = "aaaaaaaaaaaaaaaa";
        question.idQuestion = 5L;
        service.creerQuestion(question);

        Assert.fail("Exception MauvaiseQuestion non lancée");
    }


    @Test
    public void ajoutQuestionOK() throws MauvaiseQuestion {
        VDQuestion question = new VDQuestion();
        question.texteQuestion = "Aimes-tu les brownies au chocolat?";
        service.creerQuestion(question);

        Assert.assertNotNull(question.idQuestion);
    }


    @Test(expected = MauvaiseQuestion.class)
    public void ajoutQuestionKOExiste() throws MauvaiseQuestion {
        VDQuestion question = new VDQuestion();
        VDQuestion question2 = new VDQuestion();

        question.texteQuestion = "Aimes-tu les brownies au chocolat?";
        question2.texteQuestion = "Aimes-tu les BROWNIES au chocolAT?";

        service.creerQuestion(question);
        service.creerQuestion(question2);

        //TODO Ce test va fail tant que vous n'implémenterez pas toutesLesQuestions() dans ServiceImplementation
        Assert.fail("Exception MauvaiseQuestion non lancée");
    }

    @Test(expected = MauvaisVote.class)
    public void ajoutNomVotantKOVide() throws MauvaisVote {
        VDVote vote = new VDVote();
        vote.nomVotant = "";
        service.creerVote(vote);

        Assert.fail("Exception MauvaisVote non lancée");
    }

    @Test(expected = MauvaisVote.class)
    public void ajoutNomVotantKOCourt() throws MauvaisVote {
        VDVote vote = new VDVote();
        vote.nomVotant = "aaaa";
        service.creerVote(vote);

        Assert.fail("Exception MauvaisVote non lancée");
    }

    @Test(expected = MauvaisVote.class)
    public void ajoutNomVotantKOLong() throws MauvaisVote {
        VDVote vote = new VDVote();
        for (int i = 0 ; i < 256 ; i ++) vote.nomVotant += "aa";
        service.creerVote(vote);

        Assert.fail("Exception MauvaisVote non lancée");
    }

    @Test(expected = MauvaisVote.class)
    public void ajoutVoteKOIDFixe() throws MauvaisVote {
        VDVote vote = new VDVote();
        vote.nomVotant = "aa";
        vote.idVote = 5L;
        service.creerVote(vote);

        Assert.fail("Exception MauvaisVote non lancée");
    }

    @Test
    public void ajoutVoteOK() throws MauvaisVote {
        VDVote vote = new VDVote();
        vote.nomVotant = "joyce";
        service.creerVote(vote);

        Assert.assertNotNull(vote.idVote);
    }

    @Test(expected = MauvaisVote.class)
    public void ajoutVoteNul() throws MauvaisVote {
        VDVote vote = new VDVote();
        vote.nbreVote = 0;
        service.creerVote(vote);

        Assert.fail("Exception MauvaisVote non lancée");
    }


    /*
    @After
    public void closeDb() {
        bd.close();
    }
    */
}