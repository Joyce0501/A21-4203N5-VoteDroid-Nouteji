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
import com.example.votedroid.exceptions.MauvaiseSuppression;
import com.example.votedroid.modele.VDQuestion;
import com.example.votedroid.modele.VDVote;
import com.example.votedroid.service.ServiceImplementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void ajoutNomVotantKOVide() throws MauvaisVote, MauvaiseQuestion {

        VDQuestion question = new VDQuestion();
        question.texteQuestion = "Ca marche pet";
        service.creerQuestion(question);

        VDVote vote = new VDVote();
        vote.nomVotant = "";
        vote.nbreVote = 1;
        vote.questionId = question.idQuestion;
        service.creerVote(vote);

        Assert.fail("Exception MauvaisVote non lancée");
    }

    @Test(expected = MauvaisVote.class)
    public void ajoutNomVotantKOCourt() throws MauvaisVote, MauvaiseQuestion {

        VDQuestion question = new VDQuestion();
        question.texteQuestion = "Ca marche";
        service.creerQuestion(question);

        VDVote vote = new VDVote();
        vote.nomVotant = "aaa";
        vote.nbreVote = 0;
        vote.questionId = question.idQuestion;
        service.creerVote(vote);

        Assert.fail("Exception MauvaisVote non lancée");
    }

    @Test(expected = MauvaisVote.class)
    public void ajoutNomVotantKOLong() throws MauvaisVote, MauvaiseQuestion {

        VDQuestion question = new VDQuestion();
        question.texteQuestion = "Ca marche petit";
        service.creerQuestion(question);

        VDVote vote = new VDVote();
        for (int i = 0 ; i < 256 ; i ++) vote.nomVotant += "aa";
        vote.nbreVote = 0;
        vote.questionId = question.idQuestion;
        service.creerVote(vote);

        Assert.fail("Exception MauvaisVote non lancée");
    }

    @Test(expected = MauvaisVote.class)
    public void ajoutVoteKOIDFixe() throws MauvaisVote {
        VDVote vote = new VDVote();
        vote.nomVotant = "aaaaa";
        vote.idVote = 5L;
        service.creerVote(vote);

        Assert.fail("Exception MauvaisVote non lancée");
    }

    @Test
    public void ajoutVoteOK() throws MauvaisVote, MauvaiseQuestion {

        VDQuestion question = new VDQuestion();
        question.texteQuestion = "non je ne veux pas";
        service.creerQuestion(question);

        VDVote vote = new VDVote();
        vote.nomVotant = "joyce";
        vote.nbreVote = 3;
        vote.questionId = question.idQuestion;
        service.creerVote(vote);

        Assert.assertNotNull(vote.idVote);
    }

    @Test(expected = MauvaisVote.class)
    public void ajoutVoteNul() throws MauvaisVote {
        VDVote vote = new VDVote();
        service.creerVote(vote);

       Assert.fail("Exception MauvaisVote non lancée");
    }

    @Test(expected = MauvaisVote.class)
    public void ajoutVotantExiste() throws MauvaisVote, MauvaiseQuestion{

        VDQuestion question = new VDQuestion();
        question.texteQuestion = "une femme s'en vient";
        service.creerQuestion(question);

        VDVote vote1 = new VDVote();
        vote1.nomVotant = "Joycie";
        vote1.nbreVote = 2;
        vote1.questionId = question.idQuestion;

        VDVote vote2 = new VDVote();
        vote1.nomVotant = "JOYcie";
        vote1.nbreVote = 4;
        vote2.questionId = question.idQuestion;

        service.creerVote(vote1);
        service.creerVote(vote2);

        Assert.fail("Exception MauvaisVote non lancée");
    }

    @Test
    public void OrdreDescendant() throws MauvaisVote, MauvaiseQuestion {

         VDQuestion question = new VDQuestion();
         VDQuestion question1 = new VDQuestion();
         VDQuestion question2 = new VDQuestion();

        question.texteQuestion = "Ca va bien?";
        question1.texteQuestion = "Oui merci et toi?";
        question2.texteQuestion = "Ca va bien merci";

        service.creerQuestion(question);
        service.creerQuestion(question1);
        service.creerQuestion(question2);

         VDVote vote = new VDVote();
         VDVote vote1 = new VDVote();
         VDVote vote2 = new VDVote();

         vote.nbreVote = 2;
         vote.nomVotant = "Georges";
         vote.questionId = question2.idQuestion;

        vote1.nbreVote = 2;
        vote1.nomVotant = "Alice";
        vote1.questionId = question2.idQuestion;

        vote2.nbreVote = 2;
        vote2.nomVotant = "Alicia";
        vote2.questionId = question1.idQuestion;

        service.creerVote(vote);
        service.creerVote(vote1);
        service.creerVote(vote2);

        Assert.assertEquals("Ca va bien merci",service.toutesLesQuestions().get(0).texteQuestion);
        Assert.assertEquals("Oui merci et toi?",service.toutesLesQuestions().get(1).texteQuestion);
        Assert.assertEquals("Ca va bien?",service.toutesLesQuestions().get(2).texteQuestion);
    }

    @Test
    public void SupprimerVoteOK() throws MauvaiseSuppression, MauvaiseQuestion, MauvaisVote {

        VDQuestion question = new VDQuestion();
        question.texteQuestion = "Salut cava";
        service.creerQuestion(question);

        VDVote vote = new VDVote();
        vote.nomVotant = "test";
        vote.nbreVote = 3;
        vote.questionId = question.idQuestion;
        service.creerVote(vote);

        service.SupprimerVotes();

        Assert.assertEquals(0,bd.monDao().tousLesVotes().size());
        
    }

    @Test(expected = MauvaiseSuppression.class)
    public void SupprimerVotekO() throws MauvaiseSuppression {

        VDVote vote = new VDVote();
        service.SupprimerVotes();

        Assert.fail("Exception MauvaisNote non lancée");
    }

    @Test
    public void SupprimerQuestionOK() throws MauvaiseSuppression, MauvaiseQuestion, MauvaisVote {

        VDQuestion question = new VDQuestion();
        question.texteQuestion = "Oui merci";
        service.creerQuestion(question);

        VDVote vote = new VDVote();
        vote.nomVotant = "test";
        vote.nbreVote = 3;
        vote.questionId = question.idQuestion;
        service.creerVote(vote);

        service.SupprimerQuestions();

        Assert.assertEquals(0,bd.monDao().toutesLesQuestions().size());
        Assert.assertEquals(0,bd.monDao().tousLesVotes().size());

    }

    @Test(expected = MauvaiseSuppression.class)
    public void SupprimerQuestionKO() throws MauvaiseSuppression {

        VDQuestion question = new VDQuestion();
        service.SupprimerQuestions();

        Assert.fail("Exception MauvaisNote non lancée");
    }

    @Test
    public void EcartTypeKO() throws MauvaiseQuestion, MauvaisVote {
        VDQuestion question = new VDQuestion();
        question.texteQuestion = "Oui je veux une glace a la vanille";
        service.creerQuestion(question);

        VDVote vote = new VDVote();
        vote.nomVotant = "Loli";
        service.creerVote(vote);

        Assert.assertEquals(Double.NaN,service.ecartTypeVotes(question),0.0000001);

    }

    @Test
    public void EcartTypeOK() throws MauvaiseQuestion, MauvaisVote {
        VDQuestion question = new VDQuestion();
        question.texteQuestion = "Oui je veux";
        service.creerQuestion(question);

        VDVote vote = new VDVote();
        vote.nomVotant = "joyKi";
        vote.nbreVote = 5;
        vote.questionId = question.idQuestion;
        service.creerVote(vote);

        VDVote vote1 = new VDVote();
        vote1.nomVotant = "joyKia";
        vote1.nbreVote = 3;
        vote1.questionId = question.idQuestion;
        service.creerVote(vote1);

        Assert.assertEquals(1,service.ecartTypeVotes(question),0.0000001);

    }

    @Test
    public void MoyenneKO() throws MauvaisVote, MauvaiseQuestion {

        VDQuestion question = new VDQuestion();
        question.texteQuestion = "Oui je veux une glace";
        service.creerQuestion(question);

        VDVote vote = new VDVote();
        vote.nomVotant = "joyci";
        service.creerVote(vote);

        Assert.assertEquals(Double.NaN,service.moyenneVotes(question),0.0000001);
    }

    @Test
    public void MoyenneOK() throws MauvaiseQuestion, MauvaisVote {

        VDQuestion question = new VDQuestion();
        question.texteQuestion = "Oui je veux une glace";
        service.creerQuestion(question);

        VDVote vote = new VDVote();
        vote.nomVotant = "joyci";
        vote.nbreVote = 5;
        vote.questionId = question.idQuestion;
        service.creerVote(vote);

        VDVote vote1 = new VDVote();
        vote1.nomVotant = "ludooo";
        vote1.nbreVote = 3;
        vote1.questionId = question.idQuestion;
        service.creerVote(vote1);

        Assert.assertEquals(4,service.moyenneVotes(question),0.0000001);

    }

    @Test
    public void Distribution() throws MauvaiseQuestion, MauvaisVote {


        VDQuestion question = new VDQuestion();
        question.texteQuestion = "Oui je veux une poupee";
        service.creerQuestion(question);

        VDVote vote = new VDVote();
        vote.nomVotant = "llllaaaaa";
        vote.nbreVote = 5;
        vote.questionId = question.idQuestion;
        service.creerVote(vote);

        VDVote vote1 = new VDVote();
        vote1.nomVotant = "llliiii";
        vote1.nbreVote = 5;
        vote1.questionId = question.idQuestion;
        service.creerVote(vote1);

        VDVote vote2 = new VDVote();
        vote2.nomVotant = "lllloooo";
        vote2.nbreVote = 5;
        vote2.questionId = question.idQuestion;
        service.creerVote(vote2);

        VDVote vote3 = new VDVote();
        vote3.nomVotant = "llluuuu";
        vote3.nbreVote = 4;
        vote3.questionId = question.idQuestion;
        service.creerVote(vote3);

        VDVote vote4 = new VDVote();
        vote4.nomVotant = "lllleeee";
        vote4.nbreVote = 4;
        vote4.questionId = question.idQuestion;
        service.creerVote(vote4);

        VDVote vote5 = new VDVote();
        vote5.nomVotant = "llllyyyy";
        vote5.nbreVote = 1;
        vote5.questionId = question.idQuestion;
        service.creerVote(vote5);

        Map<Integer, Integer> Resultats = new HashMap<Integer, Integer>();
        Resultats.put(0,5);
        Resultats.put(1,5);
        Resultats.put(2,5);
        Resultats.put(3,4);
        Resultats.put(4,4);
        Resultats.put(5,1);

        Assert.assertEquals(Resultats, service.distributionVotes(question));

    }

    /*
    @After
    public void closeDb() {
        bd.close();
    }
    */

}