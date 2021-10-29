var submitAdd = document.querySelector('.add-submit');
var englishAdd = document.querySelector('#english-add');
var vietnameseAdd = document.querySelector('#vietnamese-add');
var addData = document.querySelector('.add-data');
var newData_ = document.querySelectorAll('.newData');
var appAdd = document.querySelector('.app-add');
var appControl = document.querySelector('.app-control');
var addStartGame = document.querySelector(".btn-add-control--start");
var appChilds = document.querySelectorAll('.app-child');
var btnSubmitNote = document.querySelector('.btn-submit-note');
var btnSubmitNew = document.querySelector('.btn-submit-new');
var btnControlBack = document.querySelector('.btn-add-control--back');
var appGame = document.querySelector('.app-game');
var appGameAnswers = document.querySelectorAll(".app-game-answers-answer");
var appGameResult = document.querySelector(".app-game-result");
var appResult = document.querySelector(".app-result");
var resultTable = document.querySelector(".app-result-table");
var newDatas = [];
var dictionaryVietnamese = [];
var dictionaryEnglish = [];
var initTableResult = `
<tr class="app-result-table-head">
<th>STT</th>
<th>Câu hỏi</th>
<th>Đáp án</th>
<th>Trả lời</th>
 </tr>
`;

class WrongResult {
    constructor(question, wrong, expected) {
        this.question = question;
        this.wrong = wrong;
        this.expected = expected;
    }
}

if(dictionaryEnglish.length >= 4) {
    btnSubmitNote.classList.add('btn-submit-note--active');
}

submitAdd.onclick = function() {
    if(englishAdd.value.length == 0 || vietnameseAdd.value.length == 0) {
        return;
    }
    let newData = document.createElement('div');
    newData.classList.add('newData');
    newData.innerHTML = `<span class="add-data-text add-data-text--english" data-english="${englishAdd.value}">${englishAdd.value}</span> <span class="add-data-text add-data-text--vietnamese" data-vietnamese="${vietnameseAdd.value}">${vietnameseAdd.value}</span><button onclick="removeNewData(${newDatas.length})" class="add-data-remove"><i class="far fa-trash-alt"></i></button>`;
    newDatas.push(newData);
    addData.appendChild(newData);
    if(document.querySelectorAll('.newData').length >= 4) {
        addStartGame.classList.add('btn-add-control--start--active');
    }
    englishAdd.value = null;
    vietnameseAdd.value = null;
};

function removeNewData(i) {
    addData.removeChild(newDatas[i]);
    newData_ = document.querySelectorAll('.newData');
    if(newData_.length < 4) {
        addStartGame.classList.remove('btn-add-control--start--active');
    }
}

btnSubmitNew.onclick = function() {
    layout(appAdd);
    loadAdd();
}


btnControlBack.onclick = function() {
    layout(appControl);
}

function loadAdd() {
    addData.innerHTML = '';
    newDatas = [];
    addStartGame.classList.remove('btn-add-control--start--active');
}

function layout(slide) {
    for(let appChild of appChilds) {
        appChild.classList.remove('layout-active');
    }
    slide.classList.add('layout-active');
}

btnSubmitNote.onclick = function() {
    appGameResult.classList.remove("layout-active--flex");
    let dataDictionary = document.getElementById("dataNote").getAttribute("data-dataNote");
    dictionaryVietnamese = [];
    dictionaryEnglish = [];
    dataDictionary = document.getElementById("dataNote").getAttribute("data-dataNote");
    for(let i = 0; i < dataDictionary.split('__').length; i++) {
        dictionaryEnglish.push(dataDictionary.split('__')[i].split('_')[0]);
        dictionaryVietnamese.push(dataDictionary.split('__')[i].split('_')[1]);
    }
    if(dictionaryEnglish.length < 4) {
        return;
    }
    let checkChosed = [];
    let wrongResults = [];
    for(let i = 0; i < dictionaryEnglish.length; i++) {
        checkChosed.push(false);
    }
    layout(appGame);
    playGame(checkChosed, wrongResults, 1);
}

addStartGame.onclick = function() {
    appGameResult.classList.remove("layout-active--flex");
    if(addData.children.length < 4) {
        return;
    }
    dictionaryVietnamese = [];
    dictionaryEnglish = [];
    let dataAddVietnameses= document.querySelectorAll(".add-data-text--vietnamese");
    let dataAddEnglishs= document.querySelectorAll(".add-data-text--english");
    for(let i = 0; i < dataAddVietnameses.length; i++) {
        dictionaryVietnamese.push(dataAddVietnameses[i].getAttribute("data-vietnamese"));
        dictionaryEnglish.push(dataAddEnglishs[i].getAttribute('data-english'));
    }
    var checkChosed = [];
    for(let i = 0; i < dictionaryEnglish.length; i++) {
        checkChosed.push(false);
    }
    let wrongResults = [];
    layout(appGame);
    playGame(checkChosed, wrongResults, 1);
}

function activeButtonNote() {
    btnSubmitNote.classList.add("btn-submit-note--active");
}

function playGame(checkChosed, wrongResults, played) {
    let chooseLanguage = Math.trunc(Math.random()*2);
    let answers = [];
    let indexQuestion;
    let question;
    let indexRightAnswer = Math.trunc(Math.random()*4);
    while(true) {
        let temp = Math.trunc(Math.random()*dictionaryEnglish.length);
        if(checkChosed[temp] == false) {
            indexQuestion = temp;
            checkChosed[indexQuestion] = true;
            if(chooseLanguage == 0) {
                answers.push(dictionaryVietnamese[indexQuestion]);
                question = dictionaryEnglish[indexQuestion];
            } else {
                answers.push(dictionaryEnglish[indexQuestion]);
                question = dictionaryVietnamese[indexQuestion];
            }
            break;
        }
    }
    let checkAnswerChosed = [];
    checkAnswerChosed.push(indexQuestion);
    while(answers.length < 4) {
        let temp = Math.trunc(Math.random()*dictionaryEnglish.length);
        let checkAnswerExist = false;
        for(let j = 0; j <checkAnswerChosed.length; j++) {
            if(checkAnswerChosed[j] == temp) {
                checkAnswerExist = true;
                break;
            }
            
        }
        if(checkAnswerExist == false) {
            checkAnswerChosed.push(temp);
            if(chooseLanguage == 0) {
                answers.push(dictionaryVietnamese[temp]);
            } else {
                answers.push(dictionaryEnglish[temp]);
            }
        }
    }
    document.querySelector(".app-game-question-main").innerHTML = question;
    appGameAnswers[indexRightAnswer].innerHTML = answers[0];
    let temp = 1;
    for(let j = 0; j < 4; j++) {
        if(j != indexRightAnswer) {
            appGameAnswers[j].innerHTML = answers[temp];
            temp++;
        }
    }
    
    document.querySelector(".app-game-quantity").innerHTML = `${played}/${checkChosed.length}`;

   for(let i = 0; i < appGameAnswers.length; i++) {
        appGameAnswers[i].onclick = function() {
        appGameResult.classList.add("layout-active--flex");
           if(i == indexRightAnswer) {
            document.querySelector(".app-game-result-right").innerHTML = ``;
               document.querySelector(".app-game-result-icon--right").classList.add("layout-active");
               document.querySelector(".app-game-result-icon--wrong").classList.remove("layout-active");
           } else {
                document.querySelector(".app-game-result-icon--wrong").classList.add("layout-active");
                document.querySelector(".app-game-result-icon--right").classList.remove("layout-active");
                document.querySelector(".app-game-result-right").innerHTML = `câu trả lời đúng là: ${answers[0]}`;
                let  wrongResult = new WrongResult(question, appGameAnswers[i].innerText, answers[0]);
                wrongResults.push(wrongResult);
           }
       }
   }

   document.querySelector(".app-game-result-continue").onclick = function() {
        appGameResult.classList.remove("layout-active--flex");
        resultTable.innerHTML = initTableResult;
        if(played != checkChosed.length) {
            playGame(checkChosed, wrongResults,played+1);
        } else {
            layout(appResult);
            let score = document.querySelector(".app-result-about-score");
            let resultRight = document.querySelector(".app-result-about-right");
            let resultWrong = document.querySelector(".app-result-about-wrong");
            score.innerHTML = score.getAttribute("data-text") + ((played - wrongResults.length) / played * 10).toString();
            resultRight.innerHTML = resultRight.getAttribute("data-text") + (played - wrongResults.length).toString();
            resultWrong.innerHTML = resultWrong.getAttribute("data-text") + wrongResults.length.toString();
            for(let j = 0; j < wrongResults.length; j++) {
                let resultWrongItem = document.createElement('tr');
                resultWrongItem.classList.add("app-result-table-content");
                resultWrongItem.innerHTML = `
                    <td class="app-result-table-item">${j+1}</td>
                    <td class="app-result-table-item">${wrongResults[j].question}</td>
                    <td class="app-result-table-item">${wrongResults[j].expected}</td>
                    <td class="app-result-table-item">${wrongResults[j].wrong}</td>
                `;
                resultTable.appendChild(resultWrongItem);
            }
        }
   } 
   document.querySelector(".app-game-result-stop").onclick = function() {
        layout(appResult);
        let score = document.querySelector(".app-result-about-score");
        let resultRight = document.querySelector(".app-result-about-right");
        let resultWrong = document.querySelector(".app-result-about-wrong");
        score.innerHTML = score.getAttribute("data-text") + ((played - wrongResults.length) / checkChosed.length * 10).toString();
        resultRight.innerHTML = resultRight.getAttribute("data-text") + (played - wrongResults.length).toString();
        resultWrong.innerHTML = resultWrong.getAttribute("data-text") + wrongResults.length.toString();
        for(let j = 0; j < wrongResults.length; j++) {
            let resultWrongItem = document.createElement('tr');
            resultWrongItem.classList.add("app-result-table-content");
            resultWrongItem.innerHTML = `
                <td class="app-result-table-item">${j+1}</td>
                <td class="app-result-table-item">${wrongResults[j].question}</td>
                <td class="app-result-table-item">${wrongResults[j].expected}</td>
                <td class="app-result-table-item">${wrongResults[j].wrong}</td>
            `;
            resultTable.appendChild(resultWrongItem);
        }
   }  
}


document.querySelector(".app-result-control-button-back").onclick = function() {
    resultTable.innerHTML = initTableResult;
    layout(appControl);
}

document.querySelector(".app-result-control-button-again").onclick = function() {
    appGameResult.classList.remove("layout-active--flex");
    resultTable.innerHTML = initTableResult;
    var checkChosed = [];
    for(let i = 0; i < dictionaryEnglish.length; i++) {
        checkChosed.push(false);
    }
    let wrongResults = [];
    layout(appGame);
    playGame(checkChosed, wrongResults, 1);
}