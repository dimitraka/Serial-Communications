function [ x ] = Ber( )
  x = 0;
  sizeOfBer = 50;
  fileId = fopen('ARQresent.txt','r');
  berArray = fscanf(fileId, '%i');
  berArray = berArray + 1;
  array = zeros(sizeOfBer);
  for i = 1 : sizeOfBer;
      pos = berArray(i);
      array(pos) = array(pos) + 1;
  end
  l = 0;
  for i = 1 : sizeOfBer;
      l = l + array(i) * i;
  end
  
  base = 16 * 8;
  x = 1 - (log10(l)/log10(base))
endfunction
